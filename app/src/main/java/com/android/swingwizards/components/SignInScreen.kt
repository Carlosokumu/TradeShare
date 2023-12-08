package com.android.swingwizards.components

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.android.swingwizards.common.PasswordSection
import com.android.swingwizards.common.UsernameSection
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.SignInViewModel


@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel) {
    val username = signInViewModel.signInFields.collectAsState().value.username
    val password = signInViewModel.signInFields.collectAsState().value.password

    Scaffold(
        topBar = {
            SignInTopBar {
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
                    text = stringResource(id = R.string.welcome_back),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.login_back),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(40.dp))
                UsernameSection(updateUserName = { username ->
                    signInViewModel.onUserNameEntered(username)
                }, username = username)
                Spacer(modifier = Modifier.height(15.dp))
                PasswordSection(password = password, updatePassword = { password ->
                    signInViewModel.onPasswordEntered(password)
                })
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { navController.navigate("reset") }) {
                        Text(
                            text = stringResource(R.string.forgot_password),
                            style = AppTheme.typography.subtitle,
                            color = AppTheme.colors.onSurface
                        )
                    }
                }

            }
            AppButton(
                text = "Sign in",
                onButtonClick = { },
                enabled = username != "" && password != ""
            )
        }
    }
}


@Composable
fun SignInTopBar(
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


@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(rememberNavController(), SignInViewModel())
}