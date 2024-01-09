package com.android.swingwizards.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.SignUpViewModel


@Composable
fun SwitchComponent(
    modifier: Modifier = Modifier,
    label: String,
    signUpViewModel: SignUpViewModel
) {
   val isCheckedValue = signUpViewModel.switchState.collectAsState().value.isChecked
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = isCheckedValue,
            onCheckedChange = { isChecked ->
               signUpViewModel.onSwitch(isSwitched = isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.secondaryVariant,
                uncheckedColor = AppTheme.colors.onSurface,
                checkmarkColor = AppTheme.colors.textPrimary
            )
        )

        Text(
            text = label,
            style = AppTheme.typography.h2,
            color = AppTheme.colors.textPrimary,
            modifier = Modifier.weight(1f)
        )

    }
}