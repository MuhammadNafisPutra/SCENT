package com.contoh.scent.ui.auth.register

data class RegisterState(
    val nameInput: String = "",
    val emailInput: String = "",
    val passwordInput: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)