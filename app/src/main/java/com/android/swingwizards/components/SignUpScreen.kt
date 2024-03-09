package com.android.swingwizards.components

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.swingwizards.R
import com.android.swingwizards.activities.Dashboard
import com.android.swingwizards.common.EmailSection
import com.android.swingwizards.common.PasswordSection
import com.android.swingwizards.common.SwitchComponent
import com.android.swingwizards.common.UsernameSection
import com.android.swingwizards.models.UiState
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils
import com.android.swingwizards.viewmodels.SignUpViewModel
import com.example.core.network.data.models.SignUpResponse
import org.koin.androidx.compose.getViewModel


@Composable
fun SignUpScreen(navController: NavController, context: Context) {

    val signUpViewModel: SignUpViewModel = getViewModel()

    val username = signUpViewModel.signUpFields.collectAsState().value.username
    val email = signUpViewModel.signUpFields.collectAsState().value.email
    val password = signUpViewModel.signUpFields.collectAsState().value.password
    val isAgreed = signUpViewModel.switchState.collectAsState().value.isChecked

    val uiState: UiState by signUpViewModel.uiState.collectAsState(initial = UiState.Relaxed)
    var isLoading by remember { mutableStateOf(false) }


    when (uiState) {
        is UiState.Error -> {
            isLoading = false
            Toast.makeText(
                context,
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
            AppUtils.launchActivity(context = context, Dashboard::class.java)
            val response = (uiState as UiState.Success<SignUpResponse>).response
            signUpViewModel.saveUserUsername(response.user.username)
            signUpViewModel.saveUserLoggedIn()
            Log.d("ServerResponse:", response.user.username)

        }

        is UiState.ServerError -> {
            isLoading = false
            val serverMessage = (uiState as UiState.ServerError)
            if (serverMessage.code == 409) {
                Toast.makeText(
                    context,
                    "The given username or email already exists",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    serverMessage.code.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        else -> {}
    }




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
                            color = AppTheme.colors.onSurface,
                            modifier = Modifier.drawBehind {
                                val strokeWidthPx = 1.dp.toPx()
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
                text = "Sign Up",
                isLoading = isLoading,
                onButtonClick = { signUpViewModel.submitUser(username, email, password) },
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

