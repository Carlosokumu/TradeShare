package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.swingwizards.common.AppLoadingWheel
import com.android.swingwizards.theme.AppTheme


@Composable
fun LoadingScreen(loadingMessage: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(AppTheme.colors.background)
            .fillMaxSize()
    ) {
        Column {
            AppLoadingWheel(contentDesc = "Loading Wheel")
            Text(
                text = loadingMessage,
                style = MaterialTheme.typography.caption,
                color = AppTheme.colors.textPrimary
            )
        }

    }


}