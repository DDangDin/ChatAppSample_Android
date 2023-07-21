package com.myschoolproject.androidchatapp.presentation.state

data class LoginState(
    val isReady: Boolean = false,
    val loading: Boolean = false,
    val error: String = ""
)
