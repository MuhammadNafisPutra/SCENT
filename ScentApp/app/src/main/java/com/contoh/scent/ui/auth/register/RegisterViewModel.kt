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

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(nameInput = name)
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(emailInput = email)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(passwordInput = password)
    }

    fun togglePasswordVisibility() {
        _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    fun registerUser() {
        val name = _state.value.nameInput.trim()
        val email = _state.value.emailInput.trim()
        val password = _state.value.passwordInput

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(errorMessage = "Semua kolom harus diisi")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(errorMessage = "Format email tidak valid")
            return
        }
        if (password.length < 6) {
            _state.value = _state.value.copy(errorMessage = "Kata sandi minimal 6 karakter")
            return
        }

        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = task.exception?.message ?: "Terjadi kesalahan"
                    )
                }
            }
    }
}