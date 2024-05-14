package com.android.swingwizards.common


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme


@Composable
fun AppAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    isFailed: Boolean = false,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {

            if (isFailed) {
                Text(text = stringResource(id = R.string.load_fail))
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .background(AppTheme.colors.secondaryVariant)
                        .border(1.dp, AppTheme.colors.secondaryVariant, CircleShape)
                ) {

                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = "Action",
                        modifier = Modifier.size(30.dp),
                        tint = AppTheme.colors.textPrimary
                    )
                }
            }
        },
        containerColor = AppTheme.colors.onPrimary,
        title = {
            Text(
                text = dialogTitle,
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.textPrimary,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = dialogText,
                style = AppTheme.typography.caption,
                color = AppTheme.colors.textPrimary
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            FilledTonalButton(
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.secondaryVariant,
                    disabledContainerColor = AppTheme.colors.secondaryVariant
                )
            ) {
                Text(
                    confirmText, style = MaterialTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )

            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                //Text("Dismiss")
            }


        }
    )

}