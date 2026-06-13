package com.contoh.scentapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.contoh.scentapp.domain.model.HeroBanner
import com.contoh.scentapp.ui.state.HomeUiState
import com.contoh.scentapp.domain.model.Product
import com.contoh.scentapp.domain.usecase.GetHomeProductsUseCase
import com.contoh.scentapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeProductsUseCase: GetHomeProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { loadProducts() }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getHomeProductsUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { products ->
                    val dynamicBanner = if (products.isNotEmpty()) {
                        val first = products.first()
                        HeroBanner(
                            productId     = first.firestoreId,
                            tag           = "NEW_ARRIVAL_TAG",
                            title         = first.name.uppercase(),
                            description   = first.description.ifBlank { "DEFAULT_DESC" },
                            imageUrl      = first.imageUrl,
                            gradientStart = first.cardColor,
                            gradientEnd   = first.accentColor
                        )
                    } else null

                    _uiState.update {
                        it.copy(
                            isLoading  = false,
                            heroBanner = dynamicBanner,
                            products   = products
                        )
                    }
                }
        }
    }

    fun toggleFavorite(productId: Int) {
        val product = _uiState.value.products.find { it.id == productId } ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(
                parfumId           = product.firestoreId,
                currentlyFavorited = product.isFavorite
            )
        }
    }
}class HomeViewModelFactory(
    private val getHomeProductsUseCase: GetHomeProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getHomeProductsUseCase, toggleFavoriteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}