package com.contoh.scentapp.data.model

data class AuthUiState(
    // Login
    val loginEmail      : String  = "",
    val loginPassword   : String  = "",
    val showLoginPass   : Boolean = false,

    // Register
    val registerName    : String  = "",
    val registerEmail   : String  = "",
    val registerPassword: String  = "",
    val showRegisterPass: Boolean = false,

    // Shared
    val isLoading       : Boolean = false,
    val errorMessage    : String? = null,
    val isLoggedIn      : Boolean = false
)