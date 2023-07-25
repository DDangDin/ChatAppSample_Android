package com.myschoolproject.androidchatapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.androidchatapp.core.common.Constants
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.domain.repository.ChattingRepository
import com.myschoolproject.androidchatapp.presentation.components.chat.ChatUiEvent
import com.myschoolproject.androidchatapp.presentation.state.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chattingRepository: ChattingRepository
): ViewModel() {

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    var inputMessage by mutableStateOf("")
        private set

    fun inputMessageChanged(value: String) {
        inputMessage = value
    }

    fun initializeChat(myName: String, friendName: String) {
        viewModelScope.launch {
            startChat(myName, friendName)

            chattingRepository.initializeChat(myName, friendName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        getChatMessages(myName, friendName)
                    }
                    is Resource.Loading -> {  }
                    is Resource.Error -> {  }
                }
            }.launchIn(viewModelScope)
        }
    }



    private fun startChat(myName: String, friendName: String) {
        viewModelScope.launch {
            chattingRepository.startChatting(myName)
        }
    }

    private fun getChatMessages(myName: String, friendName: String) {
        viewModelScope.launch {
            chattingRepository.getChatMessages(myName, friendName).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _chatState.value = chatState.value.copy(
                            chatList = result.data ?: emptyList(),
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _chatState.value = chatState.value.copy(
                            loading = true
                        )
                    }
                    is Resource.Error -> {
                        _chatState.value = chatState.value.copy(
                            loading = false,
                            error = result.message ?: Constants.ERROR_MESSAGE_UNEXPECTED
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun quitChat(myName: String, friendName: String) {
        viewModelScope.launch {
            chattingRepository.quitChatting(myName, friendName)
        }
    }

    private fun sendChat(myName: String, friendName: String, message: String) {
        viewModelScope.launch {
            chattingRepository.sendMessage(myName, friendName, message)
        }
    }

    fun onEvent(event: ChatUiEvent) {
        when(event) {
            ChatUiEvent.Error -> { Log.d("ChatUiEvent_Log", "Error Occurred!") }
            is ChatUiEvent.QuitChat -> { quitChat(event.myName, event.friendName) }
            is ChatUiEvent.SendChat -> { sendChat(event.myName, event.friendName, event.message) }
        }
    }
}