package com.myschoolproject.androidchatapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.presentation.components.login.LoginScreen
import com.myschoolproject.androidchatapp.presentation.components.main.MainScreen
import com.myschoolproject.androidchatapp.presentation.viewmodel.LoginViewModel
import com.myschoolproject.androidchatapp.presentation.viewmodel.MainViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN
    ) {

        composable(route = Routes.LOGIN_SCREEN) {

            val loginViewModel: LoginViewModel = hiltViewModel()
            val checkUser = loginViewModel.checkUserState.collectAsState()
            val loginState = loginViewModel.loginState.collectAsState()

            LoginScreen(
                nickname = loginViewModel.nickname.value,
                onNicknameChanged = loginViewModel::onNicknameChanged,
                checkUserState = checkUser.value,
                onCheckUser = { loginViewModel.checkUser(it) },
                login = { loginViewModel.login() },
                loginState = loginState.value,
                onNavigate = {
                    navController.navigate(Routes.MAIN_SCREEN) {
                        popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Routes.MAIN_SCREEN) {
            val mainViewModel: MainViewModel = hiltViewModel()
            val userListState = mainViewModel.userListState.collectAsState()
            MainScreen(
                userListState = userListState.value,
                onEvent = mainViewModel::onEvent
            )
        }

        composable(route = Routes.CHATTING_SCREEN) {

        }
    }
}