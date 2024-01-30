package com.android.swingwizards.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.swingwizards.theme.AppTheme
import com.example.core.network.data.models.Trade

@Composable
fun TradeHistoryItem(
    modifier: Modifier = Modifier,
    trade: Trade,
    onTradeClicked: (Trade) -> Unit
) {

    Surface(
        onClick = { onTradeClicked(trade) },
        color = AppTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp,
                bottom = 10.dp
            )
        ) {
            val transparentNavyColor = if(getTradeType(trade.type)) Color.Red.copy(alpha = 0.5f) else Color.Green.copy(alpha = 0.5f)

            Box(){
                Text(
                    text = if(getTradeType(trade.type)) "Sell" else "Buy",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier
                        .padding(20.dp).background(transparentNavyColor).graphicsLayer(
                            alpha = 1.0f
                        ),
                    lineHeight = 24.sp
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = trade.symbol,
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary
                )

                Text(
                    text = trade.createdAt,
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.onSurface
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.End
            ) {

                Row {

                    Text(
                        text = "P/L:",
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.textPrimary
                    )
                    Text(
                        text = "$${trade.profit}",
                        style = AppTheme.typography.subtitle,
                        color = if (isNumberNegative(trade.profit.toDouble())) AppTheme.colors.error else Color.Green
                    )
                }
                Row {
                    Text(
                        text = "Lots:",
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.textPrimary
                    )
                    Text(
                        text = trade.volume,
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.error
                    )
                }

            }
        }
    }
}


fun isNumberNegative(number: Double): Boolean {
    return number < 0.0
}


fun getTradeType(type: String) = type == "DEAL_TYPE_BUY"

@Preview
@Composable
fun TradeItemPreview() {
    TradeHistoryItem(trade = Trade("23.dp", "", "", "",""), onTradeClicked = {})
}
