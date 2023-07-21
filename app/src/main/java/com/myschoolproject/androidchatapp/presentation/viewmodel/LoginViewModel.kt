package com.myschoolproject.androidchatapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_UNEXPECTED
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import com.myschoolproject.androidchatapp.presentation.state.LoginState
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
class LoginViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    var nickname = mutableStateOf("")
        private set

    private val _checkUser = MutableStateFlow(false)
    val checkUser: StateFlow<Boolean> = _checkUser.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    init {

    }

    fun onNicknameChanged(value: String) {
        nickname.value = value
    }

    fun checkUserInfo(value: Boolean) {
        viewModelScope.launch {
            delay(1000L)
            _checkUser.update { value }
        }
    }

    fun onStart() {

        val myName = nickname.value

        viewModelScope.launch {
            chatRepository.initializeUserChat(myName).onEach { result ->
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
        }
    }
}