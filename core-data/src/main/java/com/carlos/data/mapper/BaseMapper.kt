package com.carlos.data.mapper


interface BaseMapper<ApiResponse, Domain> {


    fun asDomain(apiResponse: ApiResponse): Domain
}
