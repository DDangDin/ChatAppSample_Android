package com.myschoolproject.androidchatapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.androidchatapp.presentation.components.login.LoginScreen

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN
    ) {

        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen()
        }

        composable(route = Routes.MAIN_SCREEN) {

        }

        composable(route = Routes.CHATTING_SCREEN) {

        }
    }
}