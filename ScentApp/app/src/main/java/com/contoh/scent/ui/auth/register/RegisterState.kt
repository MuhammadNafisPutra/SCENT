package com.contoh.scent.ui.auth.register

data class RegisterState(
    val nameInput: String = "",       // Tambahan untuk Nama Lengkap
    val emailInput: String = "",
    val passwordInput: String = "",
    val isPasswordVisible: Boolean = false, // Tambahan untuk ikon mata
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)