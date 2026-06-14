package com.contoh.scentapp.ui.state

import com.contoh.scentapp.domain.model.*

data class ProfileUiState(
    val fullName        : String  = "",
    val email           : String  = "",
    val profileImageUrl : String  = "",
    val address         : String  = "",
    val isDarkMode      : Boolean = true,
    val language        : String  = "INDONESIA",
    val showDeleteDialog: Boolean = false
)

