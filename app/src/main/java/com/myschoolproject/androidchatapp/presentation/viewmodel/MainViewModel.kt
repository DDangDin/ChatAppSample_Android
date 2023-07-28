package com.myschoolproject.androidchatapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_UNEXPECTED
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import com.myschoolproject.androidchatapp.domain.repository.ChattingRepository
import com.myschoolproject.androidchatapp.presentation.components.main.MainUiEvent
import com.myschoolproject.androidchatapp.presentation.state.MyChatListState
import com.myschoolproject.androidchatapp.presentation.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chattingRepository: ChattingRepository
): ViewModel() {

    private val _userListState = MutableStateFlow(UserListState())
    val userListState: StateFlow<UserListState> = _userListState.asStateFlow()

    private val _myChatListState = MutableStateFlow(MyChatListState())
    val myChatListState: StateFlow<MyChatListState> = _myChatListState.asStateFlow()

    init {
        getUserList()
    }

    fun onEvent(event: MainUiEvent) {
        when(event) {
            is MainUiEvent.Refresh -> {
                viewModelScope.launch {
//                    Log.d("pullToRefresh", "start")
//                    delay(3000L)
//                    Log.d("pullToRefresh", "end")
                    chatRepository.getUserList().onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _userListState.update { it.copy(
                                    userList = result.data ?: emptyList(),
                                    loading = false
                                ) }
                            }

                            is Resource.Loading -> {
                                _userListState.update { it.copy(
                                    loading = true
                                ) }
                            }

                            is Resource.Error -> {
                                _userListState.update { it.copy(
                                    loading = false,
                                    error = result.message ?: ERROR_MESSAGE_UNEXPECTED
                                ) }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            chatRepository.getUserList().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _userListState.update { it.copy(
                            userList = result.data ?: emptyList(),
                            loading = false
                        ) }
                    }

                    is Resource.Loading -> {
                        _userListState.update { it.copy(
                            loading = true
                        ) }
                    }

                    is Resource.Error -> {
                        _userListState.update { it.copy(
                            loading = false,
                            error = result.message ?: ERROR_MESSAGE_UNEXPECTED
                        ) }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getMyChatList(myName: String) {
        viewModelScope.launch {
            chattingRepository.getMyChatList(myName).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _myChatListState.value = myChatListState.value.copy(
                            myChatList = result.data ?: emptyList(),
                            loading = false
                        )
                    }

                    is Resource.Error -> {
                        _myChatListState.value = myChatListState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Loading -> {
                        _myChatListState.value = myChatListState.value.copy(
                            loading = false,
                            error = result.message ?: ERROR_MESSAGE_UNEXPECTED
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}