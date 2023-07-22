package com.myschoolproject.androidchatapp.presentation.state

data class CheckUserState(
    val isSuccess: Boolean = false,
    val loading: Boolean = false,
    val error: String = ""
)
