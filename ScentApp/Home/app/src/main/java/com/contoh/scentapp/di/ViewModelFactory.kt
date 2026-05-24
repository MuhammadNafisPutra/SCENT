package com.example.scent.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scent.data.repository.impl.AuthRepositoryImpl
import com.example.scent.data.repository.impl.CartRepositoryImpl
import com.example.scent.data.repository.impl.ProductRepositoryImpl
import com.example.scent.ui.screens.auth.LoginViewModel
import com.example.scent.ui.screens.auth.RegisterViewModel
import com.example.scent.ui.screens.cart.CartViewModel
import com.example.scent.ui.screens.home.HomeViewModel
import com.example.scent.ui.screens.home.SearchViewModel
import com.example.scent.ui.screens.inventory.InventoryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object ViewModelFactory {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private val authRepo by lazy { AuthRepositoryImpl(auth, firestore) }
    private val productRepo by lazy { ProductRepositoryImpl(firestore) }

    fun loginFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepo) as T
        }
    }

    fun registerFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(authRepo) as T
        }
    }

    fun homeFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(productRepo) as T
        }
    }

    fun searchFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(productRepo) as T
        }
    }

    fun cartFactory(userId: String) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val cartRepo = CartRepositoryImpl(firestore)
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepo, userId) as T
        }
    }

    fun inventoryFactory(sellerId: String) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(productRepo, sellerId) as T
        }
    }
}
