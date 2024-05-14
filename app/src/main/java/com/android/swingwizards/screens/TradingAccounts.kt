package com.android.swingwizards.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.swingwizards.theme.AppTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import com.android.swingwizards.R
import com.android.swingwizards.components.TradingAccount
import com.android.swingwizards.components.TradingAccountItem
import com.android.swingwizards.common.TradingAccountsAppBar


@Composable
fun TradingAccounts(navController: NavController) {
    Scaffold(
        topBar = {
            TradingAccountsAppBar(
                onLeftButtonClick = {
                    navController.popBackStack()
                },
                onRightButtonClick = { },
                toolbarTitle = "Connected Accounts",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.background),
            )
        },
        modifier = Modifier.background(AppTheme.colors.background)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                //.padding(horizontal = 10.dp, vertical = 10.dp)
                .background(AppTheme.colors.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)

        ) {

            items(listOf(TradingAccount(name ="Metatrader", paymentIcon = R.drawable.metatrader, status = "Active")), key = { it.name }) { paymentMethod ->
                TradingAccountItem(
                   tradingAccount = paymentMethod,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}