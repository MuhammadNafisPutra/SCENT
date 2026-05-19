package com.contoh.scentapp.data.model

data class Product(
    val id           : Int,
    val brand        : String,
    val name         : String,
    val price        : String,
    val volume       : String,
    val cardColor    : Long,
    val accentColor  : Long         = 0xFFD4A853,
    val isFavorite   : Boolean      = false,
    val collection   : String       = "",
    val fullBrand    : String       = "",
    val description  : String       = "",
    val aromaProfile : List<String> = emptyList(),
    val usage        : String       = "",
    val rating       : Float        = 4.8f,
    val reviewCount  : Int          = 142
)

data class HeroBanner(
    val tag           : String,
    val title         : String,
    val description   : String,
    val gradientStart : Long = 0xFF1A1A1A,
    val gradientEnd   : Long = 0xFF0A0A0A
)

data class Review(
    val id          : Int,
    val initials    : String,
    val name        : String,
    val badge       : String,
    val date        : String,
    val text        : String,
    val avatarColor : Long  = 0xFF2A3545,
    val rating      : Float = 5f,
    val imageCount  : Int   = 0
)

data class SizeOption(
    val id    : String,
    val label : String,
    val size  : String,
    val price : String
)

data class AromaFilter(
    val id    : String,
    val label : String
)

data class UsageFilter(
    val id    : String,
    val label : String
)

data class CartItem(
    val productId: Int,
    val productName: String,
    val selectedSizeId: String,
    val price: String,
    val quantity: Int = 1
)

data class DetailUiState(
    val isLoading      : Boolean          = true,
    val product        : Product?         = null,
    val sizeOptions    : List<SizeOption> = emptyList(),
    val selectedSizeId : String           = "full",
    val reviews        : List<Review>     = emptyList(),
    val errorMessage   : String?          = null
)