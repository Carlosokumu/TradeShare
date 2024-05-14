package com.android.swingwizards.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.android.swingwizards.common.AppLoadingWheel
import com.android.swingwizards.theme.AppTheme

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onButtonClick: () -> Unit,
    isLoading: Boolean = false,
    showDivider: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        if (showDivider) {
            Divider(color = AppTheme.colors.onSurface)
        }
        Box(
            modifier = modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 30.dp
                )
        ) {

            if (isLoading) {
                Button(
                    onClick = { onButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.secondaryVariant,
                        disabledContainerColor = AppTheme.colors.secondaryVariant
                    ),
                    enabled = false
                ) {
                    AppLoadingWheel(contentDesc = "Loading")
                }
            } else {

                Button(
                    onClick = { onButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (enabled) 1f else 0.5f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.secondaryVariant,
                        disabledContainerColor = AppTheme.colors.secondaryVariant
                    ),
                    enabled = enabled
                ) {

                    Text(
                        text = text,
                        style = MaterialTheme.typography.button,
                        color = AppTheme.colors.textPrimary
                    )
                }
            }

        }
    }
}