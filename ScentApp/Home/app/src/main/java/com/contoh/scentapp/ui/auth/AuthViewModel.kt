package com.contoh.scentapp.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.data.model.AuthUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // ── Login field handlers ──────────────────────────────────────────────────

    fun onLoginEmailChange(value: String) {
        _uiState.update { it.copy(loginEmail = value, errorMessage = null) }
    }

    fun onLoginPasswordChange(value: String) {
        _uiState.update { it.copy(loginPassword = value, errorMessage = null) }
    }

    fun toggleLoginPasswordVisibility() {
        _uiState.update { it.copy(showLoginPass = !it.showLoginPass) }
    }

    // ── Register field handlers ───────────────────────────────────────────────

    fun onRegisterNameChange(value: String) {
        _uiState.update { it.copy(registerName = value, errorMessage = null) }
    }

    fun onRegisterEmailChange(value: String) {
        _uiState.update { it.copy(registerEmail = value, errorMessage = null) }
    }

    fun onRegisterPasswordChange(value: String) {
        _uiState.update { it.copy(registerPassword = value, errorMessage = null) }
    }

    fun toggleRegisterPasswordVisibility() {
        _uiState.update { it.copy(showRegisterPass = !it.showRegisterPass) }
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value

        // Validasi sederhana
        if (state.loginEmail.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email tidak boleh kosong") }
            return
        }
        if (state.loginPassword.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Kata sandi tidak boleh kosong") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            delay(800) // Simulasi network call
            // Untuk demo: langsung login berhasil
            _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
            onSuccess()
        }
    }

    // ── Register ──────────────────────────────────────────────────────────────

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.registerName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nama tidak boleh kosong") }
            return
        }
        if (state.registerEmail.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email tidak boleh kosong") }
            return
        }
        if (state.registerPassword.length < 6) {
            _uiState.update { it.copy(errorMessage = "Kata sandi minimal 6 karakter") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            delay(800)
            _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
            onSuccess()
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

class AuthViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}