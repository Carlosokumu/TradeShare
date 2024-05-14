package com.carlos.data.mapper

import com.carlos.model.DomainAccountMetrics
import com.carlos.model.PeriodMetrics
import com.carlos.network.models.GraphData
import com.carlos.network.models.Metrics

object MetricsMapper : BaseMapper<Metrics, DomainAccountMetrics<GraphData>> {


    override fun asDomain(apiResponse: Metrics): DomainAccountMetrics<GraphData> {
        return DomainAccountMetrics(
            dailyGrowth = apiResponse.metrics.dailyGrowth,
            bestTrade = apiResponse.metrics.bestTrade,
            wonTradesPercent = apiResponse.metrics.wonTradesPercent,
            lostTradesPercent = apiResponse.metrics.lostTradesPercent,
            worstTrade = apiResponse.metrics.worstTrade,
            dailyGain = apiResponse.metrics.dailyGain,
            balance = apiResponse.metrics.balance,
            monthlyGain = apiResponse.metrics.monthlyGain,
            daysSinceTradingStarted = apiResponse.metrics.daysSinceTradingStarted,
            expectancy = apiResponse.metrics.expectancy,
            averageWin = apiResponse.metrics.averageWin,
            averageLoss = apiResponse.metrics.averageLoss,
            monthlyAnalytics = apiResponse.metrics.monthlyAnalytics,
            periods = apiResponse.metrics.periods,
            profit = apiResponse.metrics.profit,
            deposits = apiResponse.metrics.deposits,
            risk = apiResponse.metrics.expectancy
        )
    }
}


fun Metrics.asDomain(): DomainAccountMetrics<GraphData> {
    return MetricsMapper.asDomain(this)
}