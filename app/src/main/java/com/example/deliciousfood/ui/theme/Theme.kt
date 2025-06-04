package com.example.deliciousfood.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColorScheme(
    primary = Green500 ,
    secondary = Green500 ,
    onSecondary = Color.Black ,
    surface = darkPrimary ,
    background = background ,
    onBackground = background800 ,
    primaryContainer  = davysGrey ,
    onPrimary = Color.Black ,
    onSurface = white87
)

private val LightColorPalette = lightColorScheme(
    background = navyBlue ,
    onBackground = stComandBlue ,
    surface = Color.White ,
    primary = Color.Yellow ,
    primaryContainer  = white87 ,
    secondary = purple500 ,
    onPrimary = Color.White ,
    onSecondary = Color.White ,
    error = orangeError ,
    onSurface = white87
)

@Composable
fun DeliciousFoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme() ,
    content: @Composable () -> Unit
) {
    /**
     * support just dark color for this release.
     */
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    /**
     * support just dark color for this release.
     */
    val typography = if (darkTheme) {
        DarkTypography
    } else {
        LightTypography
    }


    MaterialTheme(
        colorScheme = colors ,
        typography = typography ,
        shapes = shapes ,
        content = content
    )
}