package com.example.core.network.data.api

import com.example.core.network.data.models.ErrorResponse

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ApiCallResult<out R> {

    /**
     * This is used to represent successful responses (2xx response codes, non empty response bodies)
     */
    data class Success<out T>(val data: T) : ApiCallResult<T>()

    /**
     * Used to represent Server errors (non 2xx(5xx) status code)
     */
    data class ServerError(
        val code: Int? = null,
        val errorBody: ErrorResponse? = null
    ) : ApiCallResult<Nothing>()

    /**
     * Used to represent connectivity errors (a request that didn't result in a response)
     */
    object ApiCallError : ApiCallResult<Nothing>()
}