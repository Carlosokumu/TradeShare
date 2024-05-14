package com.carlos.model

data class DomainTrader(
    val username: String,
    val accountId: String?,
    val platform: String?,
    var roI: String = ""
)