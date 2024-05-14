package com.android.swingwizards.utils

import com.android.swingwizards.enums.TimeRange
import com.android.swingwizards.models.DataPoint
import com.carlos.model.DomainAccountMetrics
import com.carlos.network.models.GraphData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun DomainAccountMetrics<GraphData>.toDataPoint(timeRange: TimeRange): List<DataPoint> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    val todayData = this.dailyGrowth.filter { it.date == dateFormat.format(currentDate) }
    val startOfWeek = Calendar.getInstance()
    startOfWeek.firstDayOfWeek = Calendar.SUNDAY
    startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val thisWeekData = this.dailyGrowth.filter {
        dateFormat.parse(it.date)!! >= startOfWeek.time
    }

    val startOfMonth = Calendar.getInstance()
    startOfMonth.set(Calendar.DAY_OF_MONTH, 3)
    val thisMonthData = this.dailyGrowth.filter {
        dateFormat.parse(it.date)!! >= startOfMonth.time
    }


    return when (timeRange) {
        TimeRange.Day -> todayData.map { graph ->
            DataPoint(y = graph.balance, xLabel = graph.date, yLabel = graph.balance.toString())
        }

        TimeRange.Month -> thisMonthData.map { graph ->
            DataPoint(y = graph.balance, xLabel = graph.date, yLabel = graph.balance.toString())
        }

        TimeRange.Week -> thisWeekData.map { graph ->
            DataPoint(y = graph.balance, xLabel = graph.date, yLabel = graph.balance.toString())
        }
    }

}