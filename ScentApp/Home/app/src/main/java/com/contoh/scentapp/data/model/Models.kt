package com.example.scent.data.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val defaultAddress: String = "",
    val scentProfile: List<String> = emptyList()   // e.g. ["Woody", "Floral"]
)

// ─── Parfum / Product ────────────────────────────────────────────────────────
data class Parfum(
    val id: String = "",
    val sellerId: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Long = 0L,                          // in IDR
    val decantPrice: Long = 0L,
    val stock: Int = 0,
    val imageUrl: String = "",
    val description: String = "",
    val olfactoryFamily: String = "",              // Woody, Floral, Gourmand, etc.
    val topNotes: List<String> = emptyList(),
    val middleNotes: List<String> = emptyList(),
    val baseNotes: List<String> = emptyList(),
    val sizes: List<Int> = listOf(30, 50, 100),   // ml options
    val avgLongevity: Float = 0f,                  // 1–5
    val avgSillage: Float = 0f,
    val avgProjection: Float = 0f,
    val reviewCount: Int = 0,
    val isDecantAvailable: Boolean = false,
    val isLimitedDrop: Boolean = false,
    val createdAt: Long = 0L
)

// ─── Cart ────────────────────────────────────────────────────────────────────
data class CartItem(
    val id: String = "",
    val parfumId: String = "",
    val parfumName: String = "",
    val brand: String = "",
    val imageUrl: String = "",
    val selectedSize: Int = 50,
    val isDecant: Boolean = false,
    val price: Long = 0L,
    var quantity: Int = 1
) {
    val subtotal: Long get() = price * quantity
}

// ─── Order ───────────────────────────────────────────────────────────────────
data class Order(
    val id: String = "",
    val buyerId: String = "",
    val sellerId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalPrice: Long = 0L,
    val shippingCost: Long = 0L,
    val shippingAddress: String = "",
    val courier: String = "",
    val paymentMethod: String = "",
    val status: OrderStatus = OrderStatus.WAITING_PAYMENT,
    val createdAt: Long = 0L
)

enum class OrderStatus(val label: String) {
    WAITING_PAYMENT("Menunggu Pembayaran"),
    PAID("Dibayar"),
    PROCESSING("Dikemas"),
    SHIPPED("Dikirim"),
    DELIVERED("Diterima"),
    CANCELLED("Dibatalkan")
}

// ─── Review ──────────────────────────────────────────────────────────────────
data class Review(
    val id: String = "",
    val parfumId: String = "",
    val reviewerId: String = "",
    val reviewerName: String = "",
    val longevity: Float = 0f,     // 1–5
    val sillage: Float = 0f,
    val projection: Float = 0f,
    val comment: String = "",
    val photoUrls: List<String> = emptyList(),
    val createdAt: Long = 0L
) {
    val avgRating: Float get() = (longevity + sillage + projection) / 3f
}
