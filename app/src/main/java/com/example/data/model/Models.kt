package com.example.data.model

data class Product(
  val id: String,
  val name: String,
  val category: String,       // Women, Men, Kids, Shoes, Bags, Accessories, Beauty, Watches
  val subCategory: String,
  val brand: String,
  val sellerId: String,
  val sellerName: String,
  val shopName: String,
  val shopLogo: String,
  val price: Double,          // in BDT base
  val oldPrice: Double,
  val discountPercent: Int,
  val images: List<String>,
  val colors: List<String>,
  val sizes: List<String>,
  val rating: Double,
  val reviewCount: Int,
  val stock: Int,
  val description: String,
  val isBestSeller: Boolean = false,
  val isNewArrival: Boolean = false,
  val isSpecialOffer: Boolean = false,
  val isRecommended: Boolean = false
)

data class CategoryItem(
  val id: String,
  val name: String,
  val iconName: String,
  val subCategories: List<String>,
  val imageUrl: String
)

data class CartItem(
  val id: String,
  val product: Product,
  val selectedSize: String,
  val selectedColor: String,
  var quantity: Int
)

data class WishlistItem(
  val id: String,
  val product: Product
)

data class Address(
  val id: String,
  val fullName: String,
  val phone: String,
  val addressLine: String,
  val city: String,
  val postalCode: String,
  val country: String,
  val isDefault: Boolean
)

data class Review(
  val id: String,
  val productId: String,
  val userName: String,
  val rating: Double,
  val comment: String,
  val date: String
)

data class User(
  val name: String,
  val email: String,
  val phone: String,
  val avatarUrl: String
)

data class Order(
  val id: String,
  val orderNumber: String,
  val items: List<CartItem>,
  val subtotal: Double,
  val discount: Double,
  val deliveryFee: Double,
  val totalAmount: Double,
  val address: Address,
  val paymentMethod: String,
  val status: OrderStatus,
  val estimatedDate: String,
  val createdAt: String
)

enum class OrderStatus {
  ORDER_CONFIRMED,
  PROCESSING,
  PACKED,
  SHIPPED,
  OUT_FOR_DELIVERY,
  DELIVERED
}
