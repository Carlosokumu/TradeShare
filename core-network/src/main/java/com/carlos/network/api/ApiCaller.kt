package com.carlos.network.api

import android.util.Log
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.ErrorResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ApiCallResult<T> = withContext(dispatcher) {

    try {
        ApiCallResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        (Log.d("THROWABLE:", throwable.toString()))
        when (throwable) {
            is IOException -> ApiCallResult.ApiCallError
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = convertErrorBody(throwable)
                ApiCallResult.ServerError(code, errorResponse)
            }

            else -> {
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