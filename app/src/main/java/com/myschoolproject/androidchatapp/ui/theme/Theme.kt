package com.myschoolproject.androidchatapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MyDarkPrimaryColor,
    primaryContainer = MyDarkPrimaryContainerColor,
    onPrimaryContainer = MyDarkOnPrimaryContainerColor,
    secondary = MyDarkSecondaryColor,
    tertiary = MyDarkTertiaryColor,
    background = MyDarkBackgroundColor,
    surface = MyDarkSurfaceColor,
    onPrimary = MyDarkOnPrimaryColor,
    onBackground = MyDarkOnBackgroundColor,
    onSurface = MyDarkOnSurfaceColor,
    error = MyDarkErrorColor,
    outline = MyDarkOutlineColor
)

private val LightColorScheme = lightColorScheme(
    primary = MyPrimaryColor,
    primaryContainer = MyPrimaryContainerColor,
    onPrimaryContainer = MyOnPrimaryContainerColor,
    secondary = MySecondaryColor,
    tertiary = MyTertiaryColor,
    background = MyBackgroundColor,
    surface = MySurfaceColor,
    onPrimary = MyOnPrimaryColor,
    onBackground = MyOnBackgroundColor,
    onSurface = MyOnSurfaceColor,
    error = MyErrorColor,
    outline = MyOutlineColor
)

@Composable
fun AndroidChatAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            // if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            if (darkTheme) dynamicLightColorScheme(context) else dynamicLightColorScheme(context)
        }

//        darkTheme -> DarkColorScheme
        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}