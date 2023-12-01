package com.android.swingwizards.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme


@Composable
fun EmailSection(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.email),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(icon = R.drawable.email, placeHolder = R.string.email)
    }
}


@Composable
fun AccountName(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.account_name),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(icon = null, placeHolder = R.string.account_name, requiresIcon = false)
    }
}

@Composable
fun LoginId(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.login_id),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(icon = null, placeHolder = R.string.login_id, requiresIcon = false)
    }
}

@Composable
fun PasswordSection(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.password),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(icon = R.drawable.icon_lock, placeHolder = R.string.password)
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int?,
    requiresIcon: Boolean = true,
    @StringRes placeHolder: Int
) {
    Surface(
        color = AppTheme.colors.onPrimary,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.height(60.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (requiresIcon && icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "field icon",
                    modifier = Modifier.size(
                        24.dp
                    ),
                    tint = Color.DarkGray
                )
            }
            TextField(
                value = "", onValueChange = { value: String ->

                },
                placeholder = {
                    Text(
                        text = stringResource(placeHolder),
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.textPrimary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = AppTheme.colors.textPrimary,
                    unfocusedTextColor = AppTheme.colors.textPrimary ,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = AppTheme.colors.secondaryVariant
                ),
                modifier = Modifier.weight(1f),
                textStyle = AppTheme.typography.caption
            )
        }
    }
}
