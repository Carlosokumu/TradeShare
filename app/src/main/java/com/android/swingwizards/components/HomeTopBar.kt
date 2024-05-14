package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.swingwizards.R
import com.android.swingwizards.common.TradeShareCardUi
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils


@Composable
fun HomeTopBar(navController: NavController, username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            androidx.compose.material3.Text(
                text = "Hi ${username} \uD83D\uDC4B",
                modifier = Modifier.padding(16.dp),
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.textPrimary
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_notification_empty),
            contentDescription = "Connect",
            modifier = Modifier.size(20.dp),
            tint = AppTheme.colors.textPrimary
        )
    }
}


@Composable
fun Actions(onClick: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.Center) {
        items(AppUtils.actions) { action ->
            Column(
                modifier = Modifier.padding(all = 15.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { onClick(action.action) }
                        .background(AppTheme.colors.secondaryVariant)
                        .border(1.dp, AppTheme.colors.secondaryVariant, CircleShape)
                ) {

                    Icon(
                        painter = painterResource(id = action.icon),
                        contentDescription = "Action",
                        modifier = Modifier.size(30.dp),
                        tint = AppTheme.colors.textPrimary
                    )

                }
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = action.action,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(CenterHorizontally),
                    color = AppTheme.colors.textPrimary
                )
            }
        }
    }
}


@Preview
@Composable
fun ActionsPreview() {
    Actions(onClick = {})
}


@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(rememberNavController(), "carlos")
}


