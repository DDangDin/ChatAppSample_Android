package com.myschoolproject.androidchatapp.core.common

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

object Utils {

    fun changeStatusBarColor(
        context: Context,
        view: View,
        color: Color
    ) {
        // change statusBar & Icon color in only OnBoardingScreen
        val window = (context as Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = color == Color.White
    }
}