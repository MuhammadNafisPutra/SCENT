package com.contoh.scentapp.ui.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.data.model.ActiveOrder
import com.contoh.scentapp.data.model.OrderStatus
import com.contoh.scentapp.data.model.SalesProduct
import com.contoh.scentapp.data.model.SalesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalesUiState())
    val uiState: StateFlow<SalesUiState> = _uiState.asStateFlow()

    // Dialog state for resi input
    private val _resiDialogOrderId = MutableStateFlow<String?>(null)
    val resiDialogOrderId: StateFlow<String?> = _resiDialogOrderId.asStateFlow()

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading    = false,
                    products     = demoProducts(),
                    activeOrders = demoOrders()
                )
            }
        }
    }

    fun deleteProduct(productId: Int) {
        _uiState.update { state ->
            state.copy(products = state.products.filter { it.id != productId })
        }
    }

    // Step 7: Konfirmasi pembayaran transfer
    fun konfirmasiPembayaran(orderId: String) {
        _uiState.update { state ->
            state.copy(
                activeOrders = state.activeOrders.map {
                    if (it.orderId == orderId)
                        it.copy(status = OrderStatus.PEMBAYARAN_DIKONFIRMASI)
                    else it
                }
            )
        }
    }

    // Step 7: Tandai dikemas
    fun markAsPacked(orderId: String) {
        _uiState.update { state ->
            state.copy(
                activeOrders = state.activeOrders.map {
                    if (it.orderId == orderId) it.copy(status = OrderStatus.DIKEMAS) else it
                }
            )
        }
    }

    // Step 7: Open resi dialog
    fun openResiDialog(orderId: String) {
        _resiDialogOrderId.value = orderId
    }

    fun closeResiDialog() {
        _resiDialogOrderId.value = null
    }

    // Step 7: Input resi dan kirim
    fun inputResiDanKirim(orderId: String, noResi: String) {
        _uiState.update { state ->
            state.copy(
                activeOrders = state.activeOrders.map {
                    if (it.orderId == orderId)
                        it.copy(status = OrderStatus.DIKIRIM, noResi = noResi)
                    else it
                }
            )
        }
        _resiDialogOrderId.value = null
    }

    // Step 7: Update status manual
    fun updateStatus(orderId: String, status: OrderStatus) {
        _uiState.update { state ->
            state.copy(
                activeOrders = state.activeOrders.map {
                    if (it.orderId == orderId) it.copy(status = status) else it
                }
            )
        }
    }

    fun markAsShipped(orderId: String) {
        openResiDialog(orderId) // now opens resi input dialog
    }

    fun addProduct(product: SalesProduct) {
        _uiState.update { state ->
            state.copy(products = state.products + product)
        }
    }

    private fun demoProducts() = listOf(
        SalesProduct(id=201, name="NOCTURNAL OUD",  aromaFamily="WOODY",  volume="100ML", stockStatus="TERSEDIA",    price=285_000, stock=24, cardColor=0xFF1A1A1A, accentColor=0xFFD4A853),
        SalesProduct(id=202, name="ETHEREAL MIST",  aromaFamily="FLORAL", volume="50ML",  stockStatus="STOK MENIPIS",price=195_000, stock=3,  cardColor=0xFF1A2020, accentColor=0xFF8BA0B0)
    )

    private fun demoOrders() = listOf(
        ActiveOrder(orderId="SC-8921", buyerName="Julianne V.",  itemCount=2, status=OrderStatus.MENUNGGU_KONFIRMASI, paymentMethod="Transfer"),
        ActiveOrder(orderId="SC-8922", buyerName="Reza M.",      itemCount=1, status=OrderStatus.DALAM_PROSES,       paymentMethod="COD"),
        ActiveOrder(orderId="SC-8924", buyerName="Marcus L.",    itemCount=1, status=OrderStatus.DIKEMAS,            paymentMethod="Transfer"),
        ActiveOrder(orderId="SC-8925", buyerName="Sari A.",      itemCount=3, status=OrderStatus.DIKIRIM,            paymentMethod="COD", noResi="JNE123456789")
    )
}

class SalesViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesViewModel::class.java)) {
            return SalesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
