package com.android.swingwizards.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.R
import com.android.swingwizards.navigation.MetaTraderNav
import com.android.swingwizards.utils.AppUtils
import com.android.swingwizards.viewmodels.SignInViewModel
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import com.android.swingwizards.viewmodels.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpProcess : ComponentActivity() {

    private val signUpProcessViewModel: SignUpProcessViewModel by viewModel()
    private val signUpViewModel: SignUpViewModel by viewModel()
    private val signInViewModel: SignInViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.trade_share_black)
        signUpProcessViewModel.isUserSignUp()
        signUpProcessViewModel.isUserSignUp.observe(this) { isSignedUp ->
            if (isSignedUp) {
                handleIsSignedUp()
            } else {
                setContent {
                    val navController = rememberNavController()
                    MetaTraderNav(
                        navController = navController,
                        modifier = Modifier,
                        signUpProcessViewModel = signUpProcessViewModel,
                        context = this,
                        signUpViewModel = signUpViewModel,
                        signInViewModel = signInViewModel,
                        onTradingPlatformClick = {
                            signUpProcessViewModel.selectTradingPlatform(it)
                        })
                }
            }

        }
    }


    private fun handleIsSignedUp() {
        AppUtils.launchActivity(
            this,
            Dashboard::class.java
        )
        this.finish()
    }


}


