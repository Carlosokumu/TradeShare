package com.carlos.data.mapper

import com.carlos.model.DomainTrader
import com.carlos.network.models.TradersResponseDto
import com.carlos.network.models.UserResponseDto

object TradersMapper : BaseMapper<List<UserResponseDto>, List<DomainTrader>> {


    override fun asDomain(apiResponse: List<UserResponseDto>): List<DomainTrader> {
        return apiResponse.map { trader ->
            DomainTrader(
                username = trader.username,
                platform = trader.tradingAccounts?.first()?.platform,
                accountId = trader.tradingAccounts?.first()?.accountId
            )
        }

    }
}


fun List<UserResponseDto>.asDomain(): List<DomainTrader> {
    return TradersMapper.asDomain(this)
}