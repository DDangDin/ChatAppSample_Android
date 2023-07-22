package com.myschoolproject.androidchatapp.presentation.state

import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus

data class UserListState(
    val userList: List<UserStatus> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
