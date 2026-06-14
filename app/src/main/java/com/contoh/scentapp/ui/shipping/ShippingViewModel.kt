package com.contoh.scentapp.ui.shipping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.data.repository.CartRepository
import com.contoh.scentapp.data.repository.ShippingRepository
import com.contoh.scentapp.domain.model.ShippingOption
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShippingViewModel(
    private val shippingRepository: ShippingRepository = ShippingRepository.getInstance(),
    private val cartRepository: CartRepository = CartRepository.getInstance()
) : ViewModel() {

    private val _shippingOptions = MutableStateFlow<List<ShippingOption>>(cartRepository.shippingOptions)
    val shippingOptions: StateFlow<List<ShippingOption>> = _shippingOptions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _address = MutableStateFlow<String?>(null)
    val address: StateFlow<String?> = _address.asStateFlow()

    init {
        fetchDynamicShippingCosts()
        fetchAddress()
    }

    private fun fetchAddress() {
        viewModelScope.launch {
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            com.google.firebase.firestore.FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val obj = doc.get("defaultAddressObj") as? Map<String, Any>
                        if (obj != null) {
                            val nama = obj["nama"] as? String ?: ""
                            val telepon = obj["telepon"] as? String ?: ""
                            val alamat = obj["alamat"] as? String ?: ""
                            val cityName = obj["cityName"] as? String ?: ""
                            val provName = obj["provName"] as? String ?: ""
                            _address.value = "$nama - $telepon\n$alamat, $cityName, $provName"
                        } else {
                            _address.value = doc.getString("defaultAddress")
                        }
                    }
                }
        }
    }

    fun refreshAddress() {
        fetchAddress()
    }

    private fun fetchDynamicShippingCosts() {
        val destinationId = shippingRepository.selectedDestinationCityId
        if (destinationId == null) {
            _shippingOptions.value = emptyList()
            return
        }

        val originId = "city_63.71"

        viewModelScope.launch {
            _isLoading.value = true

            val couriers = listOf(
                Pair("jnt", "J&T Express"),
                Pair("sicepat", "SiCepat"),
                Pair("jne", "JNE REG")
            )

            try {
                val results = couriers.map { courier ->
                    async {
                        val result = shippingRepository.getShippingCost(
                            courier = courier.first,
                            originCityId = originId,
                            destinationCityId = destinationId
                        )
                        if (result.isSuccess) {
                            val details = result.getOrNull()
                            val firstDetail = details?.firstOrNull()

                            if (firstDetail != null) {
                                ShippingOption(
                                    id = courier.first,
                                    name = courier.second,
                                    badge = "REGULAR",
                                    estimasi = "Estimasi: ${firstDetail.estimated}",
                                    price = (firstDetail.price.toLongOrNull() ?: 0L).div(1000).toInt(),
                                    iconType = if (courier.first == "jnt") "truck" else if (courier.first == "sicepat") "lightning" else "plane"
                                )
                            } else null
                        } else null
                    }
                }.awaitAll()

                val validOptions = results.filterNotNull()
                if (validOptions.isNotEmpty()) {
                    _shippingOptions.value = validOptions
                }
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }
}
