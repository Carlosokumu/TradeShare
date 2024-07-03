package com.android.swingwizards.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.R
import com.android.swingwizards.navigation.SignUpNav
import com.android.swingwizards.utils.AppUtils
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpProcess : ComponentActivity() {

    private val signUpProcessViewModel: SignUpProcessViewModel by viewModel()

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
                    SignUpNav(
                        navController = navController,
                        signUpProcessViewModel = signUpProcessViewModel,
                        context = this,
                        modifier = Modifier
                    ) {
                        signUpProcessViewModel.selectTradingPlatform(it)
                    }
                }
            }

        }
    }


    private fun handleIsSignedUp() {
        AppUtils.launchActivity(
            this, Dashboard::class.java
        )
        this.finish()
    }


}


