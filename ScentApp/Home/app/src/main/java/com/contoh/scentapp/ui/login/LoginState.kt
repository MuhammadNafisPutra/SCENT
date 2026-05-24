package com.example.scent.ui.screens.auth

// ─── UiState ─────────────────────────────────────────────────────────────────
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccess: Boolean = false
)
