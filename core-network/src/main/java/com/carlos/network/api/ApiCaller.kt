package com.carlos.network.api

import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.ErrorResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                val errorResponse = convertErrorBody(throwable)
                ApiCallResult.ServerError(code, errorResponse)
            }

            else -> {
                ApiCallResult.ServerError(null, null)
            }
        }
    }
}


fun <T> safeFlowApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Flow<ApiCallResult<T>> = flow {
    try {
        emit(ApiCallResult.Success(apiCall.invoke()))
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> emit(ApiCallResult.ApiCallError)
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = convertErrorBody(throwable)
                emit(ApiCallResult.ServerError(code, errorResponse))
            }

            else -> {
                emit(ApiCallResult.ServerError(null, null))
            }
        }
    }
}.flowOn(dispatcher)


private fun convertErrorBody(throwable: HttpException): ErrorResponse? = try {
    throwable.response()?.errorBody()?.charStream()?.let {
        val gson = GsonBuilder()
            .create()
        gson.fromJson(it, ErrorResponse::class.java)
    }
} catch (exception: Exception) {
    null
}