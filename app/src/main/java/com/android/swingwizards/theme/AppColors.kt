package com.android.swingwizards.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class AppColors(
    primary: Color,
    onPrimary: Color,
    secondary: Color,
    secondaryVariant: Color,
    textPrimary: Color,
    background: Color,
    inverseSurface: Color,
    onSurface: Color,
    error: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var error by mutableStateOf(error)
        private set
    var isLight by mutableStateOf(isLight)
        internal set
    var background by mutableStateOf(background)
        internal set
    var onPrimary by mutableStateOf(onPrimary)
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant)
        internal set
    var inverseSurface by mutableStateOf(inverseSurface)
        internal set
    var onSurface by mutableStateOf(onSurface)
        internal set
    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        textPrimary: Color = this.textPrimary,
        background: Color = this.background,
        onPrimary: Color = this.onPrimary,
        error: Color = this.error,
        isLight: Boolean = this.isLight


    ): AppColors = AppColors(
        primary,
        onPrimary,
        secondary,
        secondaryVariant,
        textPrimary,
        background,
        inverseSurface,
        onSurface,
        error,
        isLight,

    )


    fun updateColorsFrom(other: AppColors) {
        primary = other.primary
        textPrimary = other.textPrimary
        background = other.background
        error = other.error
    }
}





private val colorDarkPrimary = Color(0xFF0D0D0D)
private val colorLightError = Color(0xFFD62222)
private  val blackBackground = Color(0xFF0D0D0D)
private  val secondary  = Color(0xFFF6F6F6)
private  val onPrimaryColor = Color(0xff252429)
private val inverseSurfaceColor =  Color(0xFFF6F6F6)
val Grey38 = Color(0xff3f3e46)
val navyBlue = Color(0xFF002060)




fun darkColors(
    primary: Color = colorDarkPrimary,
    onPrimary: Color =  onPrimaryColor,
    textPrimary: Color = secondary,
    secondaryVariant: Color = navyBlue,
    textSecondary: Color = secondary,
    onSurface: Color = Grey38,
    inverseSurface: Color = inverseSurfaceColor,
    background: Color = blackBackground,
    error: Color = colorLightError
): AppColors = AppColors(
    primary = primary,
    onPrimary = onPrimary,
    secondary = textSecondary ,
    textPrimary = textPrimary,
    background = background,
    inverseSurface = inverseSurface,
    secondaryVariant = secondaryVariant,
    error = error,
    isLight = true,
    onSurface = onSurface
)

val LocalColors = staticCompositionLocalOf { darkColors() }