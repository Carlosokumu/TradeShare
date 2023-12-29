package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.activities.AppBarWithProgress
import com.android.swingwizards.theme.AppTheme


@Composable
fun StepsComponent(onAppClick: () -> Unit) {
    Scaffold(
        topBar = { AppBarWithProgress(onBackClicked = {}) },
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(AppTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 5.dp)
            ) {
                Text(
                    text = stringResource(R.string.ctrader_rationale),
                    style = AppTheme.typography.h2,
                    color = AppTheme.colors.textPrimary,
                )
                Spacer(Modifier.size(5.dp))
                //STEPS:
                Text(
                    text = stringResource(R.string.step_one),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 5.dp,top =  20.dp)
                )
                Text(
                    text = stringResource(R.string.step_two),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 5.dp,top =  20.dp)
                )
                Text(
                    text = stringResource(R.string.step_three),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 5.dp,top =  20.dp)
                )
                Text(
                    text = stringResource(R.string.step_four),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 5.dp,top =  20.dp)
                )
                Text(
                    text = stringResource(R.string.step_five),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.padding(start = 5.dp,top =  20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                AppButton(
                    text = "Start",
                    onButtonClick = { onAppClick() },
                )
            }
        }
    }
}