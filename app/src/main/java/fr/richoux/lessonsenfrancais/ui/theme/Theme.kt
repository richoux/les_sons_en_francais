package fr.richoux.lessonsenfrancais.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

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

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SelectTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    if (darkTheme)
        MaterialTheme(
            colors = DarkColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    else
        MaterialTheme(
            colors = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
}

@Composable
fun LesSonsEnFranÃ§aisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SelectTheme(darkTheme, content)
}