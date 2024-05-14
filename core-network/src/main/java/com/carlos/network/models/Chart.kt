package com.carlos.network.models

data class EquityChart(val chart: List<Chart>)


data class Chart(val averageEquity: Double)