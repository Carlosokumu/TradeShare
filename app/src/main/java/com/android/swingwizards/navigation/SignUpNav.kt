package com.android.swingwizards.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.swingwizards.screens.PasswordReset
import com.android.swingwizards.screens.SignInScreen
import com.android.swingwizards.screens.SignUpComponent
import com.android.swingwizards.screens.SignUpScreen
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import com.carlos.core_database.entities.TradingPlatformEntity


@Composable
fun SignUpNav(
    navController: NavHostController,
    startDestination: String = "signup",
    signUpProcessViewModel: SignUpProcessViewModel,
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
            SignUpScreen(navController)
        }
        composable(route = "signin") {
            SignInScreen(navController)
        }
        composable(route = "reset") {
            PasswordReset(navController)
        }


        composable(route = "signupcomponent") {
            SignUpComponent(
                tradingPlatformEntity = selectedPlatform,
                tradingPlatForms = tradingPlatforms,
                onTradingPlatformClick = {

                    onTradingPlatformClick(it)
                                         },
                navController = navController,
                context = context
            )
        }
    }
}