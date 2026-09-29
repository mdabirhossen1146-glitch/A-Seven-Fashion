package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.*
import com.example.data.repository.FashionRepository
import kotlinx.coroutines.flow.*

class FashionViewModel : ViewModel() {
  val products = FashionRepository.products
  val categories = FashionRepository.categories
  val cartItems = FashionRepository.cartItems
  val wishlistItems = FashionRepository.wishlistItems
  val addresses = FashionRepository.addresses
  val orders = FashionRepository.orders
  val user = FashionRepository.user
  val language = FashionRepository.language
  val currency = FashionRepository.currency

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedCategoryFilter = MutableStateFlow<String?>(null)
  val selectedCategoryFilter: StateFlow<String?> = _selectedCategoryFilter.asStateFlow()

  private val _sortBy = MutableStateFlow("Newest") // Newest, Price Low to High, Price High to Low, Highest Rated, Discount, Popular
  val sortBy: StateFlow<String> = _sortBy.asStateFlow()

  private val _selectedProduct = MutableStateFlow<Product?>(null)
  val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

  private val _currentOrderForTracking = MutableStateFlow<Order?>(null)
  val currentOrderForTracking: StateFlow<Order?> = _currentOrderForTracking.asStateFlow()

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setSelectedCategoryFilter(category: String?) {
    _selectedCategoryFilter.value = category
  }

  fun setSortBy(sort: String) {
    _sortBy.value = sort
  }

  fun setSelectedProduct(product: Product?) {
    _selectedProduct.value = product
  }

  fun setCurrentOrderForTracking(order: Order?) {
    _currentOrderForTracking.value = order
  }

  fun formatPrice(amount: Double): String {
    return FashionRepository.formatPrice(amount)
  }

  fun setLanguage(lang: String) {
    FashionRepository.setLanguage(lang)
  }

  fun setCurrency(curr: String) {
    FashionRepository.setCurrency(curr)
  }

  fun addToCart(product: Product, size: String, color: String, quantity: Int = 1) {
    FashionRepository.addToCart(product, size, color, quantity)
  }

  fun removeFromCart(cartItemId: String) {
    FashionRepository.removeFromCart(cartItemId)
  }

  fun updateCartQuantity(cartItemId: String, delta: Int) {
    FashionRepository.updateCartQuantity(cartItemId, delta)
  }

  fun toggleWishlist(product: Product) {
    FashionRepository.toggleWishlist(product)
  }

  fun isProductInWishlist(productId: String): Boolean {
    return FashionRepository.isProductInWishlist(productId)
  }

  fun addAddress(address: Address) {
    FashionRepository.addAddress(address)
  }

  fun placeOrder(address: Address, paymentMethod: String, deliveryFee: Double): Order {
    return FashionRepository.placeOrder(address, paymentMethod, deliveryFee)
  }
}
