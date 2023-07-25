package com.myschoolproject.androidchatapp.presentation.components.chat

sealed class ChatUiEvent {
    data class QuitChat(val myName: String, val friendName: String): ChatUiEvent()
    data class SendChat(val myName: String, val friendName: String, val message: String): ChatUiEvent()
    object Error: ChatUiEvent()
}
