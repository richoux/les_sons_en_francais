package com.github.richoux.les_sons_en_francais.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = BlueTwitter,
    primaryVariant = LightBlue,
    secondary = BlueTwitter,
    secondaryVariant = BackgroundTwitter,
    background = BackgroundTwitter,
    surface = White
)

private val LightColorPalette = lightColors(
    primary = LightBlue,
    primaryVariant = BlueTwitter,
    secondary = BackgroundTwitter,
    secondaryVariant = LightBlue,
    background = LightBlue,
    surface = Black
)

@Composable
fun LesSonsEnFrançaisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = BlackGrey,
            darkIcons = false
        )
    }

    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}