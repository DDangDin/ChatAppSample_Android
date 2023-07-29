package com.myschoolproject.androidchatapp.navigation

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myschoolproject.androidchatapp.core.common.Constants
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.presentation.components.chat.ChatScreen
import com.myschoolproject.androidchatapp.presentation.components.login.LoginScreen
import com.myschoolproject.androidchatapp.presentation.components.main.MainScreen
import com.myschoolproject.androidchatapp.presentation.viewmodel.ChatViewModel
import com.myschoolproject.androidchatapp.presentation.viewmodel.LoginViewModel
import com.myschoolproject.androidchatapp.presentation.viewmodel.MainViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {

    val context = LocalContext.current

    val routeChatScreen = Routes.CHATTING_SCREEN

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
            val myChatListState = mainViewModel.myChatListState.collectAsState()

            val myName = CustomSharedPreference(LocalContext.current).getUserPrefs(Constants.PREFERENCE_USERNAME)

            LaunchedEffect(key1 = Unit) {
                mainViewModel.getMyChatList(myName)
            }

            MainScreen(
                userListState = userListState.value,
                onEvent = mainViewModel::onEvent,
                onNavigate = { friendName ->
                    navController.navigate("$routeChatScreen/$friendName") {
                        popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                    }
                },
                myChatListState = myChatListState.value,
            )
        }

        composable(
            route = "$routeChatScreen/{friendName}",
            arguments = listOf(
                navArgument("friendName") {
                    type = NavType.StringType
                    defaultValue = "default"
                }
            )
        ) { backStackEntry ->

            val chatViewModel: ChatViewModel = hiltViewModel()
            val friendName = backStackEntry.arguments?.getString("friendName") ?: "errorDefault"
            val myName = CustomSharedPreference(LocalContext.current).getUserPrefs(Constants.PREFERENCE_USERNAME)

            LaunchedEffect(key1 = Unit) {
                chatViewModel.initializeChat(myName, friendName)
            }

            ChatScreen(
                chatState = chatViewModel.chatState.value,
                friendName = friendName,
                onEvent = chatViewModel::onEvent,
                onPopBackStack = { navController.popBackStack() },
                inputMessage = chatViewModel.inputMessage,
                inputMessageChanged = chatViewModel::inputMessageChanged
            )
        }
    }
}