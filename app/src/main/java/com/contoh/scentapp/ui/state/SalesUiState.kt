package com.contoh.scentapp.ui.state

import com.contoh.scentapp.domain.model.*

data class SalesUiState(
    val isLoading      : Boolean          = true,
    val products       : List<SalesProduct> = emptyList(),
    val activeOrders   : List<ActiveOrder>  = emptyList(),
    val errorMessage   : String?            = null
) {
    private fun isCounted(order: ActiveOrder): Boolean {
        return order.status in listOf(
            OrderStatus.SELESAI, OrderStatus.DELIVERED
        )
    }

    val totalPendapatan: Long
        get() = activeOrders.filter { isCounted(it) }.sumOf { it.totalPrice }

    val formattedPendapatan: String
        get() = "Rp${"%,d".format(totalPendapatan).replace(',', '.')}"

    val totalPenjualan: Int
        get() = activeOrders.filter { isCounted(it) }.size

    val growthPercent: String
        get() = ""
}
