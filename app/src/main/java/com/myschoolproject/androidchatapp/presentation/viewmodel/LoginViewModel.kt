package com.myschoolproject.androidchatapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_NICKNAME_BLANK
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_UNEXPECTED
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import com.myschoolproject.androidchatapp.presentation.state.CheckUserState
import com.myschoolproject.androidchatapp.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    var nickname = mutableStateOf("")
        private set

    private val _checkUserState = MutableStateFlow(CheckUserState())
    val checkUserState: StateFlow<CheckUserState> = _checkUserState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    init {

    }

    fun onNicknameChanged(value: String) {
        nickname.value = value
    }

    fun checkUser(myName: String) {
        viewModelScope.launch {
            chatRepository.checkNickname(myName).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _checkUserState.update { it.copy(
                            isSuccess = true,
                            loading = false
                        ) }
                    }

                    is Resource.Loading -> {
                        _checkUserState.update { it.copy(
                            loading = true
                        ) }
                    }

                    is Resource.Error -> {
                        _checkUserState.update { it.copy(
                            loading = false,
                            error = result.message ?: ERROR_MESSAGE_UNEXPECTED
                        ) }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun login() {
        viewModelScope.launch {
            val myName = nickname.value.trim()
            if (myName.isNotEmpty()) {
                chatRepository.initializeUser(myName).onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _loginState.update { it.copy(
                                isReady = true,
                                loading = false
                            ) }
                        }

                        is Resource.Loading -> {
                            _loginState.update { it.copy(
                                loading = true
                            ) }
                        }

                        is Resource.Error -> {
                            _loginState.update { it.copy(
                                loading = false,
                                error = result.message ?: ERROR_MESSAGE_UNEXPECTED
                            ) }
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _loginState.update { it.copy(
                    loading = false,
                    error = ERROR_MESSAGE_NICKNAME_BLANK
                ) }
            }
        }
    }
}