package com.android.swingwizards.components

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.swingwizards.R
import com.android.swingwizards.activities.Dashboard
import com.android.swingwizards.common.PasswordSection
import com.android.swingwizards.common.UsernameSection
import com.android.swingwizards.models.UiState
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils
import com.android.swingwizards.viewmodels.SignInViewModel
import com.example.core.network.data.models.LoginResponse


@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel,context: Context) {
    val username = signInViewModel.signInFields.collectAsState().value.username
    val password = signInViewModel.signInFields.collectAsState().value.password



    val uiState: UiState by signInViewModel.uiState.collectAsState(initial = UiState.Relaxed)
    var isLoading by remember { mutableStateOf(false) }


    when (uiState) {
        is UiState.Error -> {
            isLoading = false
            Toast.makeText(
                LocalContext.current,
                "Something Went wrong.Please check your internet connection and try again",
                Toast.LENGTH_SHORT
            ).show()
        }

        UiState.Loading -> {
            isLoading = true
        }

        is UiState.Success<*> -> {
            isLoading = false
            //Move to Dashboard
            AppUtils.launchActivity(context = context,Dashboard::class.java)
            val response = (uiState as UiState.Success<LoginResponse>).response
            signInViewModel.saveUserUsername(response.user.username)
            Log.d("ServerResponse:", response.user.username)
            signInViewModel.saveUserLoggedIn()
        }

        is UiState.ServerError -> {
            isLoading = false
            val serverMessage = (uiState as UiState.ServerError)
            when(serverMessage.code){
                 404 -> {
                    Toast.makeText(
                        LocalContext.current,
                        "The given username was not found.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                401 -> {
                    Toast.makeText(
                        LocalContext.current,
                        "The password is incorrect.",
                        Toast.LENGTH_SHORT
                    ).show()
                }else -> {
                Toast.makeText(
                    LocalContext.current,
                    "An unknown error occurred while trying to log you in.",
                    Toast.LENGTH_SHORT
                ).show()
                }


            }

        }

        else -> {}
    }

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
                            color = AppTheme.colors.onSurface,
                            modifier = Modifier.drawBehind {
                                val strokeWidthPx = 2.dp.toPx()
                                val verticalOffset = size.height - 2.sp.toPx()
                                drawLine(
                                    color = Color.DarkGray,
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            }

                        )
                    }
                }

            }
            AppButton(
                text = "Sign in",
                onButtonClick = {
                     signInViewModel.signInUser(username, password)
                },
                isLoading = isLoading,
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
   // SignInScreen(rememberNavController(), SignInViewModel())
}