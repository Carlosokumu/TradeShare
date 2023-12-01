package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.swingwizards.R
import com.android.swingwizards.common.AccountName
import com.android.swingwizards.common.AccountPlatform
import com.android.swingwizards.common.LoginId
import com.android.swingwizards.common.ServerSection
import com.android.swingwizards.theme.AppTheme

@Composable
fun MetaTraderConnectionScreen() {
    Scaffold(
        topBar = {
            SignInTopBar {

            }
        },
        modifier = Modifier
            .background(AppTheme.colors.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colors.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                    .background(AppTheme.colors.background)
            ) {
                Text(
                    text = stringResource(id = R.string.connect_metatrader),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.metatrader_rationale),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                    .background(AppTheme.colors.background)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {

                Spacer(modifier = Modifier.height(20.dp))
                AccountName()
                Spacer(modifier = Modifier.height(15.dp))
                LoginId()
                Spacer(modifier = Modifier.height(15.dp))
                AccountPlatform()
                Spacer(modifier = Modifier.height(15.dp))
                ServerSection()
                Spacer(modifier = Modifier.height(15.dp))

            }
            AppButton(text = "Connect", onButtonClick = { })
        }
    }
}

@Preview
@Composable
fun MetaTraderConnectionScreenPreview() {
    MetaTraderConnectionScreen()
}