package com.android.swingwizards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme

@Composable
fun AppBar(onBackClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                all = 10.dp
            ).background(AppTheme.colors.background)
    ) {

        IconButton(onClick = { onBackClicked() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, contentDescription = "Back arrow",
                tint =  AppTheme.colors.textPrimary
            )
        }

    }
}


