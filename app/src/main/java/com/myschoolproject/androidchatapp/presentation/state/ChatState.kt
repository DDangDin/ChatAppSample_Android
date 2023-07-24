package com.myschoolproject.androidchatapp.presentation.state

import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat

data class ChatState(
    val chatList: List<Chat> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)