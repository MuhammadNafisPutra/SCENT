package com.example.scent.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scent.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(newValue: String) {
        _state.update { it.copy(email = newValue, errorMessage = null) }
    }

    fun onPasswordChange(newValue: String) {
        _state.update { it.copy(password = newValue, errorMessage = null) }
    }

    fun login() {
        val email = _state.value.email.trim()
        val password = _state.value.password

        // Basic validation
        if (email.isBlank() || !email.contains("@")) {
            _state.update { it.copy(errorMessage = "Format email tidak valid") }
            return
        }
        if (password.length < 6) {
            _state.update { it.copy(errorMessage = "Password minimal 6 karakter") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.login(email, password)

            result.fold(
                onSuccess = {
                    _state.update { it.copy(isLoading = false, isLoginSuccess = true) }
                },
                onFailure = { error ->
                    val msg = when {
                        error.message?.contains("password") == true ->
                            "Email atau password salah"
                        error.message?.contains("network") == true ->
                            "Periksa koneksi internet Anda"
                        else -> "Login gagal, coba lagi"
                    }
                    _state.update { it.copy(isLoading = false, errorMessage = msg) }
                }
            )
        }
    }

    fun resetLoginSuccess() {
        _state.update { it.copy(isLoginSuccess = false) }
    }
}
