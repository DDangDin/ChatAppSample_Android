package com.myschoolproject.androidchatapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class LoginViewModel: ViewModel() {

    var nickname = mutableStateOf("")
        private set

    fun onNicknameChanged(value: String) {
        nickname.value = value
    }
}