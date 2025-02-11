package com.example.plantapp.theme

import androidx.compose.ui.graphics.Color

val RacingGreen = Color(0xFF141718)
val SmokeyGrey = Color(0xFF757171)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF0B033F)
val Pink40 = Color(0xFF7D5260)
val Alabaster = Color(0xFFFAFAFA)
val VibrantGreen = Color(0xFF16DB16)

sealed class ThemeColors(
    val background: Color,
    val text: Color,
    val textSecondary: Color,
    val primary: Color,
    val card: Color,
    val track: Color,
    val elevation: Color
) {
    object Dark: ThemeColors(
        background = RacingGreen,
        text = Color.White,
        textSecondary = SmokeyGrey,
        primary = VibrantGreen,
        card = Color(0xFF1E1E2C),
        track = Color(0xAA3E414D),
        elevation = Color(0xFF1E1E2C)
    )
    object Light: ThemeColors(
        background = Alabaster,
        text = Color.Black,
        textSecondary = SmokeyGrey,
        primary = VibrantGreen,
        card = Color.White,
        track = Color(0x223E414D),
        elevation = Color.White
    )
}