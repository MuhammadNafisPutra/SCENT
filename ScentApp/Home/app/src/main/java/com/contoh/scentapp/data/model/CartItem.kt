package com.contoh.scentapp.data.model


data class CartItem(
    val productId: Int,
    val name: String,
    val brand: String,
    val aromaProfile: String, // ← ubah String ke List<String>
    val volume: String,
    val pricePerItem: Int,
    val quantity: Int = 1,
    val cardColor: Long = 0xFF1A1A1A,
    val accentColor: Long = 0xFFD4A853
) {
    val totalPrice     : Int    get() = pricePerItem * quantity
    val formattedPrice : String get() =
        "Rp${"%,.0f".format(totalPrice.toDouble()).replace(",", ".")}"
}




