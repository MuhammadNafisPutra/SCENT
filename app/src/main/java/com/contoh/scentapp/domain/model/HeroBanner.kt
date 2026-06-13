package com.contoh.scentapp.domain.model

data class HeroBanner(
    val productId     : String,
    val tag           : String,
    val title         : String,
    val description   : String,
    val imageUrl      : String? = null,
    val gradientStart : Long = 0xFF1A1A1A,
    val gradientEnd   : Long = 0xFF0A0A0A
)
