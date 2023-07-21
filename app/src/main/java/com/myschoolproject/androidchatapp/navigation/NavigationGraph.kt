package com.myschoolproject.androidchatapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.androidchatapp.core.common.Constants.PREFERENCE_USERNAME
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.presentation.components.login.LoginScreen
import com.myschoolproject.androidchatapp.presentation.viewmodel.LoginViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = hiltViewModel()
    val checkUser = loginViewModel.checkUser.collectAsState()
    val loginState = loginViewModel.loginState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN
    ) {

        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(
                nickname = loginViewModel.nickname.value,
                onNicknameChanged = loginViewModel::onNicknameChanged,
                checkUser = checkUser.value,
                onCheckUser = { loginViewModel.checkUserInfo(it) },
                onStart = {
//                    CustomSharedPreference(context).setUserPrefs(PREFERENCE_USERNAME, loginViewModel.nickname.value) -> 아직하면 안됨
                    loginViewModel.onStart()
                },
                loginState = loginState.value
            )
        }

        composable(route = Routes.MAIN_SCREEN) {

        }

        composable(route = Routes.CHATTING_SCREEN) {

        }
    }
}