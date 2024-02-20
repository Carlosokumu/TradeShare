package com.android.swingwizards.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.swingwizards.components.PasswordReset
import com.android.swingwizards.components.SignInScreen
import com.android.swingwizards.components.SignUpComponent
import com.android.swingwizards.components.SignUpScreen
import com.android.swingwizards.data.local.entity.TradingPlatformEntity
import com.android.swingwizards.viewmodels.SignInViewModel
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import com.android.swingwizards.viewmodels.SignUpViewModel

@Composable
fun SignUpNav(
    navController: NavHostController,
    startDestination: String = "signup",
    signUpProcessViewModel: SignUpProcessViewModel,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    context: Context,
    modifier: Modifier,
    onTradingPlatformClick: (TradingPlatformEntity) -> Unit
) {
    val tradingPlatforms by signUpProcessViewModel.tradingPlatforms.collectAsState()
    val selectedPlatform by signUpProcessViewModel.selectedPlatform.collectAsState()




    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {


        composable(route = "signup") {
            SignUpScreen(navController, signUpViewModel, context)
        }
        composable(route = "signin") {
            SignInScreen(navController, signInViewModel, context = context)
        }
        composable(route = "reset") {
            PasswordReset(navController)
        }


        composable(route = "signupcomponent") {
            SignUpComponent(
                tradingPlatformEntity = selectedPlatform,
                tradingPlatForms = tradingPlatforms,
                onTradingPlatformClick = { onTradingPlatformClick(it) },
                navController = navController,
                context = context
            )
        }
    }
}