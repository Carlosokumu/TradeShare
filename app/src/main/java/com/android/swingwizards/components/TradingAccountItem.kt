package com.android.swingwizards.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme


@Composable
fun TradingAccountItem(
    modifier: Modifier = Modifier,
    tradingAccount: TradingAccount
) {

    Surface(
        onClick = { },
        shape = RoundedCornerShape(5.dp),
        color = AppTheme.colors.onPrimary,
        modifier = modifier.padding(all = 15.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                )
        ) {

            Image(
                painter =
                painterResource(id = tradingAccount.paymentIcon),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = tradingAccount.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )

            Text(
                text = tradingAccount.status,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )

        }

    }

}


data class TradingAccount(
    val name: String,
    val status: String,
    @DrawableRes val paymentIcon: Int
)
