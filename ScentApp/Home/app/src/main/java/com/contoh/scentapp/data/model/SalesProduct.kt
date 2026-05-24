package com.contoh.scentapp.data.model

data class SalesProduct(
    val id          : Int,
    val name        : String,
    val aromaFamily : String,
    val volume      : String,
    val stockStatus : String,   // "TERSEDIA", "STOK MENIPIS", "HABIS"
    val price       : Int,
    val stock       : Int       = 0,
    val cardColor   : Long      = 0xFF1A1A1A,
    val accentColor : Long      = 0xFFD4A853
)

// ── Order Aktif ───────────────────────────────────────────────────────────────

data class ActiveOrder(
    val orderId    : String,
    val buyerName  : String,
    val itemCount  : Int,
    val status     : OrderStatus
)

enum class OrderStatus(val label: String) {
    DALAM_PROSES("DALAM PROSES"),
    DIKEMAS("DIKEMAS"),
    DIKIRIM("DIKIRIM")
}
