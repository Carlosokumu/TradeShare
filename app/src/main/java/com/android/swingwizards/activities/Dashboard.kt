package com.android.swingwizards.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.R
import com.android.swingwizards.navigation.BottomNavBar
import com.android.swingwizards.navigation.DashboardNavHost
import com.android.swingwizards.theme.AppTheme

class Dashboard : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.trade_share_black)
        window.decorView.setBackgroundColor(getColor(R.color.trade_share_black))
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isVisible = remember { mutableStateOf(true) }
            when (currentDestination?.route) {
                "connect" -> {
                    isVisible.value = false
                }

                "mt" -> {
                    isVisible.value = false
                }

                "trading_accounts" -> {
                    isVisible.value = false
                }

                else -> {
                    isVisible.value =
                        currentDestination?.route?.startsWith("trade-history/") != true
                }
            }

            Scaffold(
                topBar = {

                },
                containerColor = AppTheme.colors.background,
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.background),
                bottomBar = {
                    BottomNavBar(navController = navController, visible = isVisible.value)
                }
            ) { innerPadding ->

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(AppTheme.colors.background)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    DashboardScreen(navController, this@Dashboard)
                }


            }
        }
    }
}


@Composable
fun DashboardScreen(
    navController: NavHostController,
    context: Context
) {
    DashboardNavHost(navController = navController, context = context)
}

