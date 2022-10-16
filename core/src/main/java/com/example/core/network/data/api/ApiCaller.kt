package com.example.core.network.data.api

import android.util.Log
import com.example.core.network.data.models.ErrorResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ApiCallResult<T> = withContext(dispatcher) {
    try {
        ApiCallResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {

        when (throwable) {
            is IOException -> ApiCallResult.ApiCallError
            is HttpException -> {
                val code = throwable.code()
                Log.d("SERVERCODE", code.toString())
                val errorResponse = convertErrorBody(throwable)
                ApiCallResult.ServerError(code,errorResponse)
            }
            else -> {
                Log.d("SERVERCODE","NOT HTTP")
                ApiCallResult.ServerError(null, null)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? = try {
    throwable.response()?.errorBody()?.charStream()?.let {
        val gson = GsonBuilder()
            .create()
        gson.fromJson(it, ErrorResponse::class.java)
    }
} catch (exception: Exception) {
    null
}