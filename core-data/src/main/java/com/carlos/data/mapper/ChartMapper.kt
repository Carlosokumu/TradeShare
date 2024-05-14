package com.carlos.data.mapper

import com.carlos.model.DomainEquityChart
import com.carlos.model.DomainUser
import com.carlos.network.models.ApiResponse
import com.carlos.network.models.EquityChart

object ChartMapper : BaseMapper<EquityChart, DomainEquityChart> {


    override fun asDomain(apiResponse: EquityChart): DomainEquityChart {
        return DomainEquityChart(apiResponse.chart.map {
            it.averageEquity
        })


    }
}


fun EquityChart.asDomain(): DomainEquityChart {
    return ChartMapper.asDomain(this)
}


