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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myschoolproject.androidchatapp.R
import com.myschoolproject.androidchatapp.core.common.Constants.PREFERENCE_USERNAME
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.presentation.state.LoginState
import com.myschoolproject.androidchatapp.presentation.viewmodel.LoginViewModel
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor

// onCheckUser나 특정 작업들은 Sealed Class(UiEvent)만들어서 활용하기!!!
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    checkUser: Boolean,
    onCheckUser: (Boolean) -> Unit,
    onStart: () -> Unit,
    loginState: LoginState
) {

    val context = LocalContext.current
    val view = LocalView.current
    var isStatusBarColorChanged by remember { mutableStateOf(false) }
    val backgroundColors = listOf(Color(0xFF568BF2), MyPrimaryColor)

    val borderColor =
        if (loginState.error.isNotEmpty())
            Color.Red
        else
            Color.White

    LaunchedEffect(key1 = !isStatusBarColorChanged) {
        changeStatusBarColor(context, view, Color(0xFF568BF2))
        isStatusBarColorChanged = true
    }

    LaunchedEffect(key1 = Unit) {
        if (!CustomSharedPreference(context).isContain(PREFERENCE_USERNAME)) {
            onCheckUser(true)
        }
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
            verticalArrangement = Arrangement.spacedBy(
                20.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Icon(
                modifier = Modifier.size(65.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
                contentDescription = "logo",
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.service_name),
                fontSize = 40.sp,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 29.dp, end = 29.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            if (loginState.loading || !checkUser) {
                CircularProgressIndicator(
                    strokeWidth = 1.5.dp,
                    color = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            } else {
                if (loginState.error.isNotEmpty()) {
                    Text(
                        text = loginState.error,
                        color = borderColor,
                        textAlign = TextAlign.Center
                    )
                }
                CustomTextField(
                    text = nickname,
                    onTextChanged = onNicknameChanged,
                    borderColor = borderColor
                ) // 키보드 올라 왔을 때 약간의 여백 줘야 함
                CustomButton2(
                    text = R.string.login_btn_text,
                    onClick = { onStart() }
                )
            }
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
    LoginScreen(
        nickname = "",
        onNicknameChanged = {},
        checkUser = true,
        onCheckUser = {},
        onStart = {},
        loginState = LoginState()
    )
}