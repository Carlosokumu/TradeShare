package com.android.swingwizards.common

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme


@Composable
fun AccountPlatform(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        androidx.compose.material.Text(
            text = stringResource(id = R.string.mt_version),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownMenu(items = listOf("mt4", "mt5"))
    }
}

@Composable
fun ServerSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        androidx.compose.material.Text(
            text = stringResource(id = R.string.server),
            color = AppTheme.colors.textPrimary,
            style = AppTheme.typography.subtitle
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropdownMenu(items = listOf("HFMarketsKE-Live Server 8", "HFMarketsKE-Demo Server 2"))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(items: List<String>) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(items[0]) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(AppTheme.colors.onPrimary),
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            modifier = Modifier.background(AppTheme.colors.onPrimary),
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .focusProperties {
                        canFocus = false
                    }
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = AppTheme.colors.textPrimary,
                    unfocusedTextColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = AppTheme.colors.secondaryVariant,
                ),
                textStyle = AppTheme.typography.caption,

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.None,
                )

            )

            ExposedDropdownMenu(
                expanded = expanded,
                modifier = Modifier.background(AppTheme.colors.onPrimary),
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.textPrimary
                            )
                        },
                        modifier = Modifier.background(AppTheme.colors.onPrimary),
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun DropDownMenuPreview() {
    DropdownMenu(listOf())
}