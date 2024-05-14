package com.carlos.data.mapper

import com.carlos.model.DomainAccountTrades
import com.carlos.model.DomainUser
import com.carlos.network.models.AccountTrades
import com.carlos.network.models.ApiResponse

object AccountTradesMapper : BaseMapper<AccountTrades, DomainAccountTrades> {


    override fun asDomain(apiResponse: AccountTrades): DomainAccountTrades {
        return DomainAccountTrades(
            positions = apiResponse.positions
        )
    }
}


fun AccountTrades.asDomain(): DomainAccountTrades {
    return AccountTradesMapper.asDomain(this)
}