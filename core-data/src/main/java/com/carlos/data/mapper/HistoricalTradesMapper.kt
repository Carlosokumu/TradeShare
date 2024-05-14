package com.carlos.data.mapper

import com.carlos.model.DomainClosedTrade
import com.carlos.network.models.ClosedTrade

object HistoricalTradesMapper : BaseMapper<ClosedTrade, DomainClosedTrade> {


    override fun asDomain(apiResponse: ClosedTrade): DomainClosedTrade {
        return DomainClosedTrade(trades = apiResponse.trades)


    }
}


fun ClosedTrade.asDomain(): DomainClosedTrade {
    return HistoricalTradesMapper.asDomain(this)
}
