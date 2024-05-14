package com.android.swingwizards.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme
import com.carlos.core_database.entities.TradingPlatformEntity


@Composable
fun TradingPlatformItem(
    tradingPlatform: TradingPlatformEntity,
    modifier: Modifier,
    onPlatformClick: (TradingPlatformEntity) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(
            1.dp,
            if (tradingPlatform.isSelected) AppTheme.colors.inverseSurface else AppTheme.colors.onSurface
        ),
        onClick = {
            onPlatformClick(tradingPlatform)
        },
        color = AppTheme.colors.onPrimary,
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
            Image(
                painter = painterResource(id = tradingPlatform.logo),
                contentDescription = tradingPlatform.name + " logo",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(50.dp)
                    .width(50.dp),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = tradingPlatform.name, style = AppTheme.typography.caption,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier.weight(1f)
            )

            if (tradingPlatform.isSelected)
                Icon(
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = "Done Icon",
                    tint = AppTheme.colors.inverseSurface
                )
        }
    }
}





