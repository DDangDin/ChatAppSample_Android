package com.myschoolproject.androidchatapp.presentation.components.login

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myschoolproject.androidchatapp.R
import com.myschoolproject.androidchatapp.presentation.viewmodel.LoginViewModel
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val view = LocalView.current
    var isStatusBarColorChanged by remember { mutableStateOf(false) }
    val backgroundColors = listOf(Color(0xFF568BF2), MyPrimaryColor)
    val viewModel = viewModel<LoginViewModel>()

    LaunchedEffect(key1 = !isStatusBarColorChanged) {
        changeStatusBarColor(context, view, Color(0xFF568BF2))
        isStatusBarColorChanged = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColors,
                )
            )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier.size(65.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
                contentDescription = "logo",
                tint = Color.White
            )
            Text(
                text = "Simple Chat",
                fontSize = 40.sp,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 29.dp, end = 29.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.CenterVertically)
        ) {
            CustomTextField(
                text = viewModel.nickname.value,
                onTextChanged = viewModel::onNicknameChanged
            ) // 키보드 올라 왔을 때 약간의 여백 주기
            CustomButton2(
                text = R.string.login_btn_text,
                onClick = {  }
            )
        }
    }
}

fun changeStatusBarColor(
    context: Context,
    view: View,
    color: Color
) {
    // change statusBar & Icon color in only OnBoardingScreen
    val window = (context as Activity).window
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}