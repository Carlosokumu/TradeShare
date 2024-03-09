package com.android.swingwizards.enums


enum class HomeFeedContentType(
    val contentType: ContentType,
    val option: String
) {
    FOR_YOU(ContentType.FOR_YOU, "For You"),
    ECONOMIC_CALENDAR(ContentType.ECONOMIC_CALENDAR, "Economic Calendar")

}


enum class ContentType {
    FOR_YOU,
    ECONOMIC_CALENDAR,
}