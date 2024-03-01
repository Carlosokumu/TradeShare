package com.android.swingwizards.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.setSaturation

@Composable
fun TradeShareCardUi() {
    val bankCardAspectRatio = 2.186f
    Card(
        modifier = Modifier
            .wrapContentSize()
            .background(AppTheme.colors.background)
            .padding(30.dp)
            .aspectRatio(bankCardAspectRatio),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.background(AppTheme.colors.secondaryVariant).wrapContentSize()) {
            CardBackground(baseColor = AppTheme.colors.secondaryVariant)
            Column {
                Text(
                    text = "Welcome to TradeShare \uD83D\uDC4B",
                    modifier = Modifier.padding(16.dp),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = "Verify your account to start Investing !",
                    modifier = Modifier.padding(start = 16.dp, top = 5.dp),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
                Button(
                    onClick = {},
                    border = BorderStroke(width = 1.dp,color = Color.Gray),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 16.dp,top = 10.dp)
                ) {
                    Text(
                        text = "Verify",
                        modifier = Modifier,
                        style = AppTheme.typography.button,
                        color = AppTheme.colors.textPrimary
                    )
                }
            }

        }
    }
}


@Composable
fun CardBackground(baseColor: Color) {
    val colorSaturation75 = baseColor.setSaturation(0.75f)
    val colorSaturation50 = baseColor.setSaturation(10f)
    // Drawing Shapes with Canvas
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(baseColor)
    ) {
        // Drawing Circles
        drawCircle(
            color = colorSaturation75,
            center = Offset(x = size.width * 0.2f, y = size.height * 0.6f),
            radius = size.minDimension * 0.85f
        )
        drawCircle(
            color = colorSaturation75,
            center = Offset(x = size.width * 0.2f, y = size.height * 0.6f),
            radius = size.minDimension * 0.85f
        )
        drawCircle(
            color = colorSaturation50,
            center = Offset(x = size.width * 0.1f, y = size.height * 0.3f),
            radius = size.minDimension * 0.75f
        )
    }
}


@Composable
@Preview
fun TradeShareCardUiPreview() {
    Box(Modifier.padding(16.dp)) {
        TradeShareCardUi()
    }
}