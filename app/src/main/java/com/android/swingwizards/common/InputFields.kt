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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme


@Composable
fun EmailSection(
    modifier: Modifier = Modifier,
    email: String = "",
    updateEmail: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.email),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            icon = R.drawable.email,
            placeHolder = R.string.email,
            value = email,
            updateText = updateEmail
        )
    }
}


@Composable
fun UsernameSection(
    modifier: Modifier = Modifier,
    updateUserName: (String) -> Unit,
    username: String = ""
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.username),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            icon = R.drawable.person_outline,
            value = username,
            placeHolder = R.string.username,
            updateText = updateUserName
        )
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
        InputField(
            icon = null,
            placeHolder = R.string.account_name,
            requiresIcon = false,
            updateText = {})
    }
}

@Composable
fun LoginId(modifier: Modifier = Modifier,updateLoginId: (String) -> Unit,loginId: String) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.login_id),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            icon = null,
            placeHolder = R.string.login_id,
            value = loginId ,
            requiresIcon = false,
            shouldBeNumber = true,
            updateText = updateLoginId)
    }
}

@Composable
fun PasswordSection(
    modifier: Modifier = Modifier,
    updatePassword: (String) -> Unit,
    password: String = "",
    shouldShowIcon: Boolean = true
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.password),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            icon = R.drawable.icon_lock,
            placeHolder = R.string.password,
            updateText = updatePassword,
            value = password,
            isPasswordField = true,
            shouldShowIcon = shouldShowIcon
        )
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int?,
    isPasswordField: Boolean = false,
    value: String = "",
    updateText: (String) -> Unit,
    requiresIcon: Boolean = true,
    shouldBeNumber: Boolean = false,
    @StringRes placeHolder: Int,
    shouldShowIcon: Boolean = true
) {
    var passwordVisible by rememberSaveable { mutableStateOf(true) }

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
            if (requiresIcon && icon != null &&  shouldShowIcon) {
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
                value = value, onValueChange = { value: String ->
                    updateText(value)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = if (shouldBeNumber) KeyboardType.Number else KeyboardType.Text
                ),
                placeholder = {
                    Text(
                        text = stringResource(placeHolder),
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.textPrimary
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = AppTheme.colors.textPrimary,
                    unfocusedTextColor = AppTheme.colors.textPrimary,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = AppTheme.colors.secondaryVariant
                ),
                modifier = Modifier.weight(1f),
                textStyle = AppTheme.typography.caption,
                trailingIcon = {
                    if (isPasswordField) {
                        val image =
                            if (passwordVisible) painterResource(id = R.drawable.visible) else painterResource(
                                id = R.drawable.visible_off
                            )
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, description)
                        }
                    }
                }
            )
        }
    }
}
