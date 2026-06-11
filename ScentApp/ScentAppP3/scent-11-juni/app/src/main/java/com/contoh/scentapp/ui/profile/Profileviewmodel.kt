package com.contoh.scentapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.data.model.ProfileUiState
import com.contoh.scentapp.data.repository.LanguageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) { // ✅ Ganti ke AndroidViewModel

    private val languageManager = LanguageManager.getInstance(application) // ✅ Tambahkan ini

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            language = getLanguageLabel(languageManager.selectedLanguage) // ✅ Dinamis
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private fun getLanguageLabel(langCode: String): String {
        return when (langCode) {
            "en" -> "ENGLISH"
            else -> "INDONESIA"
        }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun showDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun hideDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun confirmDeleteAccount() {
        viewModelScope.launch {
            hideDeleteDialog()
        }
    }
}

class ProfileViewModelFactory : ViewModelProvider.Factory { // ✅ Sesuaikan Factory
    private val application: Application

    constructor(application: Application) {
        this.application = application
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}