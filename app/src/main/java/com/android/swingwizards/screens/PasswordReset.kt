package com.android.swingwizards.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.R
import com.android.swingwizards.common.EmailSection
import com.android.swingwizards.common.AppBar
import com.android.swingwizards.common.AppButton
import com.android.swingwizards.theme.AppTheme


@Composable
fun PasswordReset(navController: NavController) {
    Scaffold(
        topBar = {
            PasswordResetTopBar {
                navController.navigateUp()
            }
        },
        modifier = Modifier.background(AppTheme.colors.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colors.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp
                    )
                    .weight(1f)
            ) {

                Text(
                    text = stringResource(id = R.string.password_reset),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.password_info),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(25.dp))
                EmailSection(updateEmail = {})
            }
            AppButton(text = "Reset", onButtonClick = { }, enabled = false)
        }
    }
}


@Preview
@Composable
fun PasswordResetPreview() {
    PasswordReset(rememberNavController())
}

@Composable
fun PasswordResetTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxWidth()
    ) {
        AppBar(onBackClicked = onBackClicked)
    }
}