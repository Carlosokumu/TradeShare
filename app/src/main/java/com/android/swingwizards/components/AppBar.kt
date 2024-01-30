package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme

@Composable
fun AppBar(onBackClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                all = 10.dp
            )
            .background(AppTheme.colors.background)
    ) {

        IconButton(onClick = { onBackClicked() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, contentDescription = "Back arrow",
                tint = AppTheme.colors.textPrimary
            )
        }

    }
}


@Composable
fun TradesTopBar(onBackClicked: () -> Unit, username: String,onSwitchPeriodClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(
                all = 5.dp
            )
            .fillMaxWidth()
            .background(AppTheme.colors.background)
    ) {


        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
                .background(
                    color = AppTheme.colors.onPrimary,
                    shape = CircleShape
                )
        ) {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Back arrow",
                    modifier = Modifier.size(30.dp),
                    tint = AppTheme.colors.onSurface
                )
            }

        }
        Text(
            text = username,
            style = AppTheme.typography.subtitle,
            color = AppTheme.colors.textPrimary
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
                .background(
                    color = AppTheme.colors.onPrimary,
                    shape = CircleShape
                )
        ) {
            IconButton(onClick = { onSwitchPeriodClick() }) {
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_swap),
                    contentDescription = "Action",
                    modifier = Modifier.size(30.dp),
                    tint = AppTheme.colors.onSurface
                )
            }
        }
    }
}


@Preview
@Composable
fun TradesTopBarPreview() {
    TradesTopBar(username = "Carlos ", onBackClicked = {}, onSwitchPeriodClick = {})
}


