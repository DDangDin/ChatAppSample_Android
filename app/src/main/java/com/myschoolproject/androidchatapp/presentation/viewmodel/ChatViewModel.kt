package com.myschoolproject.androidchatapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import com.myschoolproject.androidchatapp.domain.repository.ChattingRepository
import com.myschoolproject.androidchatapp.presentation.state.ChatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chattingRepository: ChattingRepository
): ViewModel() {

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    fun startChat(myName: String) {
        viewModelScope.launch {
            chattingRepository.startChatting(myName)
        }
    }

    fun quitChat(myName: String) {
        viewModelScope.launch {
            chattingRepository.quitChatting(myName)
        }
    }


}