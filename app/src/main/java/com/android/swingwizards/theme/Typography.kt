package com.android.swingwizards.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.android.swingwizards.R

private val gotham = FontFamily(
    Font(R.font.gotham, FontWeight.Normal)
)
private val lato = FontFamily(
    Font(R.font.lato, FontWeight.Normal)
)

private val robotoLight = FontFamily(
    Font(R.font.robotolight, FontWeight.Normal)
)
private val open = FontFamily(
    Font(R.font.open, FontWeight.Normal)
)

data class AppTypography(
    val h1: TextStyle = TextStyle(
        fontFamily = gotham,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    val h2: TextStyle = TextStyle(
        fontFamily = robotoLight,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    val subtitle: TextStyle = TextStyle(
        fontFamily = gotham,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily = gotham,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
internal val LocalTypography = staticCompositionLocalOf { AppTypography() }