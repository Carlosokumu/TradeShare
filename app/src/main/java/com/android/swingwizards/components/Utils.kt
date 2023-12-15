package com.android.swingwizards.components

import androidx.compose.runtime.Composable

data class Component(
    val component: @Composable() () -> Unit
)