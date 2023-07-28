package com.myschoolproject.androidchatapp.presentation.components.login

import android.util.Log
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.R
import com.myschoolproject.androidchatapp.core.common.Constants.PREFERENCE_USERNAME
import com.myschoolproject.androidchatapp.core.common.Utils.changeStatusBarColor
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.presentation.state.CheckUserState
import com.myschoolproject.androidchatapp.presentation.state.LoginState
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor

// onCheckUser나 특정 작업들은 Sealed Class(UiEvent)만들어서 활용하기!!!
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChanged: (String) -> Unit,
    checkUserState: CheckUserState,
    onCheckUser: (String) -> Unit,
    login: () -> Unit,
    loginState: LoginState,
    onNavigate: () -> Unit
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
        if (CustomSharedPreference(context).isContain(PREFERENCE_USERNAME)) {
            val username = CustomSharedPreference(context).getUserPrefs(PREFERENCE_USERNAME)
            Log.d("SharedPreference_Log", username)
            onCheckUser(username)
        }
    }
    LaunchedEffect(key1 = checkUserState) {
        if (checkUserState.isSuccess) {
            onNavigate()
        }
    }

    LaunchedEffect(key1 = loginState) {
        if (loginState.isReady) {
            CustomSharedPreference(context).setUserPrefs(PREFERENCE_USERNAME, nickname.trim())
            onNavigate()
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
            if (checkUserState.loading) {
                CircularProgressIndicator(
                    strokeWidth = 1.5.dp,
                    color = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            } else {
                if (loginState.loading) {
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
                        onClick = { login() },
                        backgroundColor = Color.Transparent,
                        borderEnabled = true
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        nickname = "",
        onNicknameChanged = {},
        checkUserState = CheckUserState(),
        onCheckUser = {},
        login = {},
        loginState = LoginState(),
        onNavigate = {}
    )
}