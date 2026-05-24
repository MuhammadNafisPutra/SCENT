package com.example.scent.domain.repository

import com.example.scent.data.model.*
import kotlinx.coroutines.flow.Flow

// ─── Auth ────────────────────────────────────────────────────────────────────
interface AuthRepository {
    val currentUserId: String?
    val isLoggedIn: Boolean

    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String, fullName: String): Result<User>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
}

// ─── Product ─────────────────────────────────────────────────────────────────
interface ProductRepository {
    fun getParfumList(): Flow<List<Parfum>>
    fun searchParfum(query: String, filters: ParfumFilter): Flow<List<Parfum>>
    suspend fun getParfumById(id: String): Parfum?
    suspend fun addParfum(parfum: Parfum): Result<String>       // returns new doc ID
    suspend fun updateParfum(parfum: Parfum): Result<Unit>
    suspend fun deleteParfum(id: String): Result<Unit>
    fun getSellerParfums(sellerId: String): Flow<List<Parfum>>
    fun getReviews(parfumId: String): Flow<List<Review>>
    suspend fun addReview(review: Review): Result<Unit>
}

// ─── Cart ────────────────────────────────────────────────────────────────────
interface CartRepository {
    fun getCartItems(userId: String): Flow<List<CartItem>>
    suspend fun addToCart(userId: String, item: CartItem): Result<Unit>
    suspend fun updateQuantity(userId: String, itemId: String, quantity: Int): Result<Unit>
    suspend fun removeFromCart(userId: String, itemId: String): Result<Unit>
    suspend fun clearCart(userId: String): Result<Unit>
}

// ─── Order ───────────────────────────────────────────────────────────────────
interface OrderRepository {
    fun getBuyerOrders(buyerId: String): Flow<List<Order>>
    fun getSellerOrders(sellerId: String): Flow<List<Order>>
    suspend fun createOrder(order: Order): Result<String>
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Unit>
}

// ─── User ────────────────────────────────────────────────────────────────────
interface UserRepository {
    suspend fun getUserById(uid: String): User?
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun updateScentProfile(uid: String, scentProfile: List<String>): Result<Unit>
}

// ─── Filter helper ───────────────────────────────────────────────────────────
data class ParfumFilter(
    val olfactoryFamily: String? = null,
    val topNotes: List<String> = emptyList(),
    val middleNotes: List<String> = emptyList(),
    val baseNotes: List<String> = emptyList(),
    val isDecantOnly: Boolean = false,
    val isLimitedDrop: Boolean = false,
    val usage: String? = null  // "Siang" | "Malam"
)
