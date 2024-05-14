package com.android.swingwizards.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.swingwizards.R


sealed class Screen(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {
    object HomeFeed : Screen(HOME_FEED, R.string.home_feed, R.drawable.ic_home)
    object Traders : Screen(TRADERS, R.string.traders, R.drawable.ic_crowd)

    object Portfolio : Screen(PORTFOLIO, R.string.portfolio, R.drawable.pie_chart)
    object Account : Screen(ACCOUNT, R.string.account_profile, R.drawable.account)


    companion object {
        const val HOME_FEED = "home_feed"
        const val TRADERS = "traders"
        const val PORTFOLIO = "portfolio"
        const val ACCOUNT = "account"

    }
}