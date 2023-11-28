package com.android.swingwizards.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.R

@Composable
fun WhoAreYouComponent(
    modifier: Modifier = Modifier,
    onTraderClicked: () -> Unit,
    onInvestorClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(top = 10.dp, start = 5.dp)
            .scrollable(scrollState, Orientation.Vertical)
    ) {
        Text(
            text = stringResource(id = R.string.who_are_you),
            style = AppTheme.typography.h1,
            color = AppTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.change_who),
            style = AppTheme.typography.subtitle,
            color = AppTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, AppTheme.colors.textPrimary, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_investor),
                    contentDescription = "Investor icon",
                    modifier = Modifier.size(20.dp),
                    tint = AppTheme.colors.secondary
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.investor),
                    style = MaterialTheme.typography.subtitle1,
                    color =AppTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.investor_caption),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )

            }

            IconButton(onClick = { onInvestorClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "right arrow",
                    modifier = Modifier.size(20.dp),
                    tint = AppTheme.colors.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, AppTheme.colors.textPrimary, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chart),
                    contentDescription = "Business icon",
                    modifier = Modifier.size(20.dp),
                    tint = AppTheme.colors.secondary
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.trader),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.trader_caption),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )

            }

            IconButton(onClick = { onTraderClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "right arrow",
                    modifier = Modifier.size(20.dp),
                    tint = AppTheme.colors.secondary

                )
            }
        }
    }
}
