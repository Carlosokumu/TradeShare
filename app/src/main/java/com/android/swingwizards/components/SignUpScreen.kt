package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.android.swingwizards.common.SwitchComponent
import com.android.swingwizards.common.UsernameSection
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.SignUpViewModel


@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel) {
    val username = signUpViewModel.signUpFields.collectAsState().value.username
    val email = signUpViewModel.signUpFields.collectAsState().value.email
    val password = signUpViewModel.signUpFields.collectAsState().value.password
    val isAgreed = signUpViewModel.switchState.collectAsState().value.isChecked

    Scaffold(
        topBar = {
            SignUpTopBar()
        },
        modifier = Modifier.background(AppTheme.colors.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
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
                    text = stringResource(id = R.string.started),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.account),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(40.dp))
                EmailSection(
                    email = email,
                    updateEmail = { email ->
                        signUpViewModel.onEmailEntered(email)
                    })
                Spacer(modifier = Modifier.height(15.dp))
                UsernameSection(
                    username = username,
                    updateUserName = { username ->
                        signUpViewModel.onUserNameEntered(username)
                    })
                Spacer(modifier = Modifier.height(15.dp))
                PasswordSection(updatePassword = { password ->
                    signUpViewModel.onPasswordEntered(password)
                }, password = password)
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SwitchComponent(
                        label = stringResource(R.string.i_agree_to_terms),
                        modifier = Modifier.fillMaxWidth(),
                        signUpViewModel = signUpViewModel
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.textPrimary
                    )

                    TextButton(onClick = { navController.navigate("signin") }) {
                        Text(
                            text = stringResource(id = R.string.sign_in),
                            style = AppTheme.typography.subtitle,
                            color = AppTheme.colors.onSurface
                        )
                    }
                }
            }
            AppButton(
                text = "Sign Up",
                onButtonClick = { },
                enabled = email != "" && username != "" && password != "" && isAgreed
            )
        }
    }
}

@Composable
fun SignUpTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxWidth()
            .padding(all = 10.dp)
    ) {
        Spacer(modifier = Modifier.size(25.dp))
    }
}

@Preview()
@Composable
fun SignUpPreview() {
    SignUpScreen(rememberNavController(), SignUpViewModel())
}