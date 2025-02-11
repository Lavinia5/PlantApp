package com.example.plantapp.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.plantapp.ui.theme.Pink80

private val DarkColorScheme = darkColorScheme(
    primary = ThemeColors.Dark.primary,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = ThemeColors.Dark.text,
    onSecondary = ThemeColors.Dark.textSecondary,
    background = ThemeColors.Dark.background,
    surface = ThemeColors.Dark.card,
    tertiaryContainer = ThemeColors.Dark.track,
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeColors.Light.primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = ThemeColors.Light.text,
    onSecondary = ThemeColors.Light.textSecondary,
    background = ThemeColors.Light.background,
    surface = ThemeColors.Light.card,
    tertiaryContainer = ThemeColors.Light.track
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EventAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}