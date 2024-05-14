package com.android.swingwizards.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.swingwizards.common.LineChart
import com.android.swingwizards.mapCoinWithMarketDataUiItemsList
import com.android.swingwizards.models.DataPoint
import com.android.swingwizards.screens.dataPoints
import com.android.swingwizards.theme.AppTheme
import com.carlos.model.DomainTrader


@Composable
fun TraderItem(index: String, trader: DomainTrader, navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        Log.d("CLICKED:", "Clicked")
        navController.navigate("trade-history/${trader.username}/${trader.accountId}")
    }

    ) {
        Row(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
                .clickable(onClick = {
                    // navController.navigate("trade-history/${trader.username}/${trader.accountId}")
                }), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.width(14.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Username",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Text(
                    text = trader.username,
                    color = Color.Gray,
                    style = AppTheme.typography.caption,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))

            }
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Platform",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = trader.platform ?: "-",
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "ROI",
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Text(
                    text = trader.roI,
                    fontWeight = FontWeight.Normal,
                    color = Color.Green,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

        }
        Spacer(modifier = Modifier.width(14.dp))
        Row(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(14.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "TimeFrame",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "6 months",
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Risk",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "6", fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Trend",
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.subtitle,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    LineChart(
                        modifier = Modifier.size(width = 50.dp, height = 15.dp),
                        data = mapCoinWithMarketDataUiItemsList(dataPoints),
                        graphColor = Color.Red,
                        showDashedLine = true,
                        showYLabels = false
                    )

                }

            }
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))


    }
}


val dataPoints = listOf(
    DataPoint(y = -23.2, xLabel = " ", yLabel = " "),
    DataPoint(y = 22.6, xLabel = "", yLabel = ""),
)
