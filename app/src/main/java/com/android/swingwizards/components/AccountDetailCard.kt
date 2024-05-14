package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme

@Composable
fun AccountDetailsCard(
    keyValues: Map<String, Any> = mutableMapOf(
        Pair("No of Trades", "11"),
        Pair("Won Trades", "5"),
        Pair("Lost Trades", "6"),
        Pair("Win Rate", "45.45"),
        Pair("Loss Rate", "54.54")
    )
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0xFFebdfe1).copy(alpha = 0.1f),
                        Color(0xFFebdfe1).copy(alpha = 0.03f),
                    ), center = Offset(30f, -30f), radius = 200.0f
                )
            )
            .border(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFebdfe1).copy(alpha = 0.0f), Color(0xFFebdfe1).copy(alpha = 0.15f)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY,

                    ),

                width = 1.dp, shape = RoundedCornerShape(18.dp)
            )
            .padding(all = 14.dp)
    ) {

        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.Info,
                    "info",
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Trading Statistics",
                    color = AppTheme.colors.textPrimary.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.body,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            for (item in keyValues) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp)
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${item.key}:",
                        color = AppTheme.colors.textPrimary.copy(alpha = 0.75f),
                        textAlign = TextAlign.Start,
                        style = AppTheme.typography.subtitle
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${item.value}",
                        color = AppTheme.colors.textPrimary.copy(alpha = 0.75f),
                        textAlign = TextAlign.Start,
                        style = AppTheme.typography.caption
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

            }


        }

    }

}


@Preview
@Composable
fun SensorDetailPreview() {
    AccountDetailsCard()
}