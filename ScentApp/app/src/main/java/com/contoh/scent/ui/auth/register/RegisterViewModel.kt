package com.contoh.scent.ui.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(emailInput = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(passwordInput = password)
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    fun registerUser() {
        val email = _state.value.emailInput.trim()
        val password = _state.value.passwordInput

        // Validasi
        if (email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(errorMessage = "Email dan Password tidak boleh kosong")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(errorMessage = "Format email tidak valid")
            return
        }
        if (password.length < 6) {
            _state.value = _state.value.copy(errorMessage = "Password minimal 6 karakter")
            return
        }

        // Lolos validasi, mulai loading
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = task.exception?.message ?: "Terjadi kesalahan saat mendaftar"
                    )
                }
            }
    }
}