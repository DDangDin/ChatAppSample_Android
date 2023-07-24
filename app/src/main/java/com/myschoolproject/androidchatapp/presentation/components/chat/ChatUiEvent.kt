package com.myschoolproject.androidchatapp.presentation.components.chat

sealed class ChatUiEvent {
    data class StartChat(val myName: String): ChatUiEvent()
    data class QuitChat(val myName: String): ChatUiEvent()
    object Error: ChatUiEvent()
}
