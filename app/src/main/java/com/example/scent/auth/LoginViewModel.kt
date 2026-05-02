package com.example.scent.ui.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEmailChange(newValue: String) {
        _state.value = _state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _state.value = _state.value.copy(password = newValue)
    }

    fun login() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        val currentEmail = _state.value.email
        val currentPassword = _state.value.password

        println("Memproses Login: $currentEmail")
    }
}