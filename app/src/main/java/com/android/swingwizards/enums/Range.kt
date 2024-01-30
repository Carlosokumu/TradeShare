package com.android.swingwizards.enums

enum class TradeHistoryRange(
    val timeRange: TimeRange,
    val uiString: String
) {
    Day(TimeRange.Day, "24H"),
    Week(TimeRange.Week, "1W"),
    Month(TimeRange.Month, "1M"),

}




enum class TimeRange {
    Day,
    Week,
    Month,
}