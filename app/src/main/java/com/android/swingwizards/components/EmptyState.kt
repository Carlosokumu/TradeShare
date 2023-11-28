package com.android.swingwizards.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme


@Composable
fun EmptyState(modifier: Modifier = Modifier, emptyMessage: String,isRegistered: Boolean = true) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wizard_empty))
        LottieAnimation(
            modifier = modifier.size(100.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
        Spacer(modifier = Modifier.size(14.dp))
        Text(
            text = emptyMessage,
            style = MaterialTheme.typography.subtitle1,
            color = AppTheme.colors.textPrimary
        )
        if (isRegistered){
             AppButton(text = "Register", showDivider = false, onButtonClick = {})
        }
    }
}


@Preview
@Composable
fun EmptyStatePreview(){
    EmptyState(emptyMessage = "You  have no account")
}
