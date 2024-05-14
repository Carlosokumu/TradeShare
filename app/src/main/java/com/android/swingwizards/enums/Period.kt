package com.android.swingwizards.enums


enum class PeriodHistoryRange(
    val periodRange: PeriodRange,
    val uiString: String
) {
    Day(PeriodRange.Day, "Today"),
    Week(PeriodRange.Week, "Last Week"),
    Month(PeriodRange.Month, "A month ago"),

}


enum class PeriodRange {
    Day,
    Week,
    Month,
}