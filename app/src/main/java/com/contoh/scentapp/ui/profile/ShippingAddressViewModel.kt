package com.contoh.scentapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.data.remote.dto.CityDto
import com.contoh.scentapp.data.remote.dto.ProvinceDto
import com.contoh.scentapp.data.repository.ShippingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShippingAddressViewModel(
    private val shippingRepository: ShippingRepository = ShippingRepository.getInstance()
) : ViewModel() {

    private val _provinces = MutableStateFlow<List<ProvinceDto>>(emptyList())
    val provinces: StateFlow<List<ProvinceDto>> = _provinces.asStateFlow()

    private val _cities = MutableStateFlow<List<CityDto>>(emptyList())
    val cities: StateFlow<List<CityDto>> = _cities.asStateFlow()

    private val _isLoadingProvinces = MutableStateFlow(false)
    val isLoadingProvinces: StateFlow<Boolean> = _isLoadingProvinces.asStateFlow()

    private val _isLoadingCities = MutableStateFlow(false)
    val isLoadingCities: StateFlow<Boolean> = _isLoadingCities.asStateFlow()

    private val _savedAddressObj = MutableStateFlow<Map<String, Any>?>(null)
    val savedAddressObj: StateFlow<Map<String, Any>?> = _savedAddressObj.asStateFlow()

    init {
        fetchProvinces()
        fetchSavedAddress()
    }

    private fun fetchProvinces() {
        viewModelScope.launch {
            _isLoadingProvinces.value = true
            shippingRepository.getProvinces().onSuccess {
                _provinces.value = it
            }.onFailure {
                // handle error
            }
            _isLoadingProvinces.value = false
        }
    }

    fun fetchCities(provinceId: String) {
        viewModelScope.launch {
            _isLoadingCities.value = true
            shippingRepository.getCities(provinceId).onSuccess {
                _cities.value = it
            }.onFailure {
                // handle error
            }
            _isLoadingCities.value = false
        }
    }

    fun saveDestinationCity(cityId: String) {
        shippingRepository.selectedDestinationCityId = "city_$cityId"
    }

    fun saveStructuredAddress(nama: String, telepon: String, alamat: String, kodePos: String, provId: String, provName: String, cityId: String, cityName: String, label: String, isUtama: Boolean) {
        viewModelScope.launch {
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val fullAddress = "$nama - $telepon\n$alamat, $cityName, $provName"
            val addressObj = mapOf(
                "nama" to nama,
                "telepon" to telepon,
                "alamat" to alamat,
                "kodePos" to kodePos,
                "provId" to provId,
                "provName" to provName,
                "cityId" to cityId,
                "cityName" to cityName,
                "label" to label,
                "isUtama" to isUtama
            )
            com.google.firebase.firestore.FirebaseFirestore.getInstance().collection("users").document(uid)
                .set(mapOf(
                    "defaultAddress" to fullAddress,
                    "defaultAddressObj" to addressObj
                ), com.google.firebase.firestore.SetOptions.merge())
        }
    }

    private fun fetchSavedAddress() {
        viewModelScope.launch {
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            com.google.firebase.firestore.FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val obj = doc.get("defaultAddressObj") as? Map<String, Any>
                        _savedAddressObj.value = obj
                    }
                }
        }
    }
}
