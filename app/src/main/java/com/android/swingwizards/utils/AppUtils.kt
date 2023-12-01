package com.android.swingwizards.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.android.swingwizards.R
import com.android.swingwizards.models.Action
import com.android.swingwizards.models.Screen


object AppUtils {
    fun launchActivity(context: Context, targetActivity: Class<out ComponentActivity>) {
        val intent = Intent(context, targetActivity)
        context.startActivity(intent)
    }



    val navigationScreens = listOf(
        Screen.Home,
        Screen.Trades,
        Screen.Insights,
        Screen.Account
    )



    val actions = listOf(
        Action(action = "Connect", icon = R.drawable._ic_add),
        Action(action = "Buy", icon = R.drawable.ic_swap),
        Action(action = "Deposit", icon = R.drawable.ic_deposit),
        Action(action = "Withdraw", icon = R.drawable.ic_withdraw)

    )
}


