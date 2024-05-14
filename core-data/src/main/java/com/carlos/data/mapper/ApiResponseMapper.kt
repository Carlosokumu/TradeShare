package com.carlos.data.mapper

import com.carlos.model.DomainUser
import com.carlos.network.models.ApiResponse
import com.carlos.network.models.UserResponseDto

object ApiResponseMapper : BaseMapper<ApiResponse, DomainUser> {


    override fun asDomain(apiResponse: ApiResponse): DomainUser {
        return DomainUser(
            username = apiResponse.userResponseDto.username,
            email = apiResponse.userResponseDto.username,
            accessToken = apiResponse.token
        )
    }
}


fun ApiResponse.asDomain(): DomainUser {
    return ApiResponseMapper.asDomain(this)
}