package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FashionRepository {

  private val _products = MutableStateFlow(
    listOf(
      Product(
        id = "p1",
        name = "Elegant Embroidered Silk Saree",
        category = "Women",
        subCategory = "Saree",
        brand = "A Seven Atelier",
        sellerId = "s1",
        sellerName = "Aiman Chowdhury",
        shopName = "A Seven Fashion Flagship",
        shopLogo = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
        price = 4850.0,
        oldPrice = 6500.0,
        discountPercent = 25,
        images = listOf(
          "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600",
          "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=600"
        ),
        colors = listOf("Royal Blue", "Crimson Red", "Emerald Green"),
        sizes = listOf("Free Size"),
        rating = 4.8,
        reviewCount = 142,
        stock = 25,
        description = "Luxurious Jamdani-inspired silk saree with intricate gold zari work, perfect for weddings, Eid, and formal gatherings.",
        isBestSeller = true,
        isNewArrival = true,
        isRecommended = true
      ),
      Product(
        id = "p2",
        name = "Designer Salwar Kameez Set",
        category = "Women",
        subCategory = "Salwar Kameez",
        brand = "Dhaka Vogue",
        sellerId = "s2",
        sellerName = "Tanvir Ahmed",
        shopName = "Dhaka Vogue House",
        shopLogo = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150",
        price = 3200.0,
        oldPrice = 4000.0,
        discountPercent = 20,
        images = listOf(
          "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600",
          "https://images.unsplash.com/photo-1515372039744-b8f02a3ae446?w=600"
        ),
        colors = listOf("Pastel Pink", "Mint Green", "Lavender"),
        sizes = listOf("S", "M", "L", "XL"),
        rating = 4.6,
        reviewCount = 98,
        stock = 40,
        description = "Chiffon unstitched 3-piece salwar kameez with embroidered neckline and chiffon dupatta.",
        isBestSeller = true,
        isSpecialOffer = true
      ),
      Product(
        id = "p3",
        name = "Classic Linen Casual Shirt for Men",
        category = "Men",
        subCategory = "Shirts",
        brand = "Royal Bengal Threads",
        sellerId = "s3",
        sellerName = "Rahim Uddin",
        shopName = "Bengal Men's Wear",
        shopLogo = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150",
        price = 1850.0,
        oldPrice = 2400.0,
        discountPercent = 22,
        images = listOf(
          "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600",
          "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600"
        ),
        colors = listOf("White", "Sky Blue", "Beige"),
        sizes = listOf("M", "L", "XL", "XXL"),
        rating = 4.7,
        reviewCount = 210,
        stock = 60,
        description = "Breathable 100% pure linen casual shirt tailored for comfort in tropical weather.",
        isBestSeller = true,
        isNewArrival = true,
        isRecommended = true
      ),
      Product(
        id = "p4",
        name = "Premium Cotton Festive Panjabi",
        category = "Men",
        subCategory = "Panjabi",
        brand = "A Seven Atelier",
        sellerId = "s1",
        sellerName = "Aiman Chowdhury",
        shopName = "A Seven Fashion Flagship",
        shopLogo = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
        price = 2950.0,
        oldPrice = 3800.0,
        discountPercent = 22,
        images = listOf(
          "https://images.unsplash.com/photo-1617137984095-74e4e5e3613f?w=600",
          "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=600"
        ),
        colors = listOf("Off-White", "Maroon", "Navy Blue"),
        sizes = listOf("S", "M", "L", "XL", "XXL"),
        rating = 4.9,
        reviewCount = 315,
        stock = 35,
        description = "Elegant designer panjabi featuring subtle machine embroidery on collar and cuffs.",
        isBestSeller = true,
        isSpecialOffer = true,
        isRecommended = true
      ),
      Product(
        id = "p5",
        name = "Kids Cute Floral Party Dress",
        category = "Kids",
        subCategory = "Girls",
        brand = "Tiny Trendz",
        sellerId = "s4",
        sellerName = "Nusrat Jahan",
        shopName = "Tiny Tots Fashion",
        shopLogo = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150",
        price = 1250.0,
        oldPrice = 1600.0,
        discountPercent = 21,
        images = listOf(
          "https://images.unsplash.com/photo-1518831959646-742c3a14ebf7?w=600",
          "https://images.unsplash.com/photo-1622290291468-a28f7a7dc6a8?w=600"
        ),
        colors = listOf("Pink", "Yellow", "White"),
        sizes = listOf("XS", "S", "M", "L"),
        rating = 4.5,
        reviewCount = 64,
        stock = 50,
        description = "Adorable cotton blend party frock with bow accent and soft inner lining.",
        isNewArrival = true
      ),
      Product(
        id = "p6",
        name = "Boys Smart Denim Jacket & Tee",
        category = "Kids",
        subCategory = "Boys",
        brand = "Tiny Trendz",
        sellerId = "s4",
        sellerName = "Nusrat Jahan",
        shopName = "Tiny Tots Fashion",
        shopLogo = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150",
        price = 1650.0,
        oldPrice = 2100.0,
        discountPercent = 21,
        images = listOf(
          "https://images.unsplash.com/photo-1519238263530-99bdd11df2ea?w=600",
          "https://images.unsplash.com/photo-1503944583220-7f2679b360f4?w=600"
        ),
        colors = listOf("Blue Denim", "Black"),
        sizes = listOf("S", "M", "L", "XL"),
        rating = 4.7,
        reviewCount = 82,
        stock = 45,
        description = "Stylish washed denim jacket paired with a comfortable cotton graphic tee.",
        isSpecialOffer = true
      ),
      Product(
        id = "p7",
        name = "Unisex Classic Leather Sneakers",
        category = "Shoes",
        subCategory = "Shoes",
        brand = "Urban Walk",
        sellerId = "s5",
        sellerName = "Kamal Hossain",
        shopName = "Urban Walk Footwear",
        shopLogo = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=150",
        price = 2890.0,
        oldPrice = 3600.0,
        discountPercent = 19,
        images = listOf(
          "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600",
          "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=600"
        ),
        colors = listOf("White/Red", "All White", "Black"),
        sizes = listOf("39", "40", "41", "42", "43", "44"),
        rating = 4.8,
        reviewCount = 175,
        stock = 55,
        description = "Premium synthetic leather sneakers designed for daily urban comfort and durable traction.",
        isBestSeller = true,
        isRecommended = true
      ),
      Product(
        id = "p8",
        name = "Women's Elegant High Heel Sandals",
        category = "Shoes",
        subCategory = "Shoes",
        brand = "Glamour Steps",
        sellerId = "s5",
        sellerName = "Kamal Hossain",
        shopName = "Urban Walk Footwear",
        shopLogo = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=150",
        price = 2450.0,
        oldPrice = 3100.0,
        discountPercent = 20,
        images = listOf(
          "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=600",
          "https://images.unsplash.com/photo-1535043934128-cf0b28d52f95?w=600"
        ),
        colors = listOf("Nude", "Black", "Gold"),
        sizes = listOf("36", "37", "38", "39", "40"),
        rating = 4.6,
        reviewCount = 92,
        stock = 30,
        description = "Stunning minimalist strappy heels with cushioned insoles for party and wedding glam.",
        isNewArrival = true
      ),
      Product(
        id = "p9",
        name = "Genuine Leather Executive Tote Bag",
        category = "Bags",
        subCategory = "Bags",
        brand = "Artisan Hide",
        sellerId = "s6",
        sellerName = "Farhana Hoque",
        shopName = "Artisan Leather Co.",
        shopLogo = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150",
        price = 4200.0,
        oldPrice = 5500.0,
        discountPercent = 23,
        images = listOf(
          "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=600",
          "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=600"
        ),
        colors = listOf("Tan Brown", "Jet Black", "Burgundy"),
        sizes = listOf("Standard"),
        rating = 4.9,
        reviewCount = 130,
        stock = 20,
        description = "Spacious grain leather tote bag with laptop compartment and secure zip closure.",
        isBestSeller = true,
        isRecommended = true
      ),
      Product(
        id = "p10",
        name = "Men's Waterproof Travel Backpack",
        category = "Bags",
        subCategory = "Bags",
        brand = "Urban Gear",
        sellerId = "s6",
        sellerName = "Farhana Hoque",
        shopName = "Artisan Leather Co.",
        shopLogo = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150",
        price = 2100.0,
        oldPrice = 2800.0,
        discountPercent = 25,
        images = listOf(
          "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600",
          "https://images.unsplash.com/photo-1622560480605-d83c853bc5c3?w=600"
        ),
        colors = listOf("Charcoal Grey", "Navy", "Black"),
        sizes = listOf("35L"),
        rating = 4.7,
        reviewCount = 188,
        stock = 45,
        description = "Ergonomic USB-charging port backpack suitable for college, office, and weekend travel.",
        isSpecialOffer = true
      ),
      Product(
        id = "p11",
        name = "Minimalist Stainless Steel Chronograph Watch",
        category = "Watches",
        subCategory = "Watches",
        brand = "Chronos",
        sellerId = "s7",
        sellerName = "Imran Khan",
        shopName = "Chronos Timepieces",
        shopLogo = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=150",
        price = 5500.0,
        oldPrice = 7500.0,
        discountPercent = 26,
        images = listOf(
          "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600",
          "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=600"
        ),
        colors = listOf("Silver/Black", "Rose Gold", "All Black"),
        sizes = listOf("Standard"),
        rating = 4.9,
        reviewCount = 240,
        stock = 15,
        description = "Luxurious Japanese quartz movement watch with scratch-resistant mineral crystal glass.",
        isBestSeller = true,
        isNewArrival = true,
        isRecommended = true
      ),
      Product(
        id = "p12",
        name = "Women's Rose Gold Mesh Watch & Bracelet Set",
        category = "Watches",
        subCategory = "Watches",
        brand = "Chronos",
        sellerId = "s7",
        sellerName = "Imran Khan",
        shopName = "Chronos Timepieces",
        shopLogo = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=150",
        price = 4800.0,
        oldPrice = 6200.0,
        discountPercent = 22,
        images = listOf(
          "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=600",
          "https://images.unsplash.com/photo-1533139502658-0198f920d8e8?w=600"
        ),
        colors = listOf("Rose Gold", "Silver"),
        sizes = listOf("Standard"),
        rating = 4.8,
        reviewCount = 112,
        stock = 25,
        description = "Elegant gift set featuring a dainty rose gold watch paired with a matching crystal charm bracelet.",
        isSpecialOffer = true
      ),
      Product(
        id = "p13",
        name = "Gold-Plated Traditional Jhumka Earrings",
        category = "Accessories",
        subCategory = "Accessories",
        brand = "Aura Jewels",
        sellerId = "s8",
        sellerName = "Sabina Yasmin",
        shopName = "Aura Fashion Jewelry",
        shopLogo = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150",
        price = 950.0,
        oldPrice = 1300.0,
        discountPercent = 26,
        images = listOf(
          "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?w=600",
          "https://images.unsplash.com/photo-1630019852942-f89202989a59?w=600"
        ),
        colors = listOf("Antique Gold", "Ruby Red", "Emerald"),
        sizes = listOf("Standard"),
        rating = 4.7,
        reviewCount = 150,
        stock = 70,
        description = "Exquisite handcrafted traditional bell earrings with pearl drops for festive elegance.",
        isBestSeller = true
      ),
      Product(
        id = "p14",
        name = "UV400 Gradient Sunglasses for Men & Women",
        category = "Accessories",
        subCategory = "Accessories",
        brand = "SunStyle",
        sellerId = "s8",
        sellerName = "Sabina Yasmin",
        shopName = "Aura Fashion Jewelry",
        shopLogo = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150",
        price = 1150.0,
        oldPrice = 1500.0,
        discountPercent = 23,
        images = listOf(
          "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600",
          "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=600"
        ),
        colors = listOf("Black/Gold", "Tortoiseshell", "Silver"),
        sizes = listOf("Standard"),
        rating = 4.5,
        reviewCount = 88,
        stock = 60,
        description = "Classic aviator style sunglasses with 100% UV protection and lightweight metal frame.",
        isNewArrival = true
      ),
      Product(
        id = "p15",
        name = "Matte Velvet Liquid Lipstick Set (4 Pcs)",
        category = "Beauty",
        subCategory = "Beauty",
        brand = "Glow Cosmetics",
        sellerId = "s9",
        sellerName = "Taslima Nasrin",
        shopName = "Glow & Glamour",
        shopLogo = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
        price = 1450.0,
        oldPrice = 1900.0,
        discountPercent = 23,
        images = listOf(
          "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=600",
          "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?w=600"
        ),
        colors = listOf("Nude Kit", "Red Kit", "Pink Kit"),
        sizes = listOf("Standard"),
        rating = 4.8,
        reviewCount = 195,
        stock = 40,
        description = "Long-lasting, smudge-proof, highly pigmented matte liquid lipsticks infused with vitamin E.",
        isBestSeller = true,
        isRecommended = true
      ),
      Product(
        id = "p16",
        name = "Herbal Organic Glow Face Serum (30ml)",
        category = "Beauty",
        subCategory = "Beauty",
        brand = "Botanica",
        sellerId = "s9",
        sellerName = "Taslima Nasrin",
        shopName = "Glow & Glamour",
        shopLogo = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
        price = 1750.0,
        oldPrice = 2300.0,
        discountPercent = 23,
        images = listOf(
          "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600",
          "https://images.unsplash.com/photo-1608248597359-994b7fa6b701?w=600"
        ),
        colors = listOf("Clear"),
        sizes = listOf("30ml"),
        rating = 4.9,
        reviewCount = 220,
        stock = 50,
        description = "Vitamin C and Hyaluronic acid rejuvenating serum for deep hydration and radiant glow.",
        isSpecialOffer = true
      ),
      Product(
        id = "p17",
        name = "Women's Designer Georgette Party Gown",
        category = "Women",
        subCategory = "Dresses",
        brand = "A Seven Atelier",
        sellerId = "s1",
        sellerName = "Aiman Chowdhury",
        shopName = "A Seven Fashion Flagship",
        shopLogo = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
        price = 5200.0,
        oldPrice = 6800.0,
        discountPercent = 23,
        images = listOf(
          "https://images.unsplash.com/photo-1566174053879-31528523f8ae?w=600",
          "https://images.unsplash.com/photo-1539109136881-3be0616acf4b?w=600"
        ),
        colors = listOf("Dusty Rose", "Emerald", "Midnight Blue"),
        sizes = listOf("S", "M", "L", "XL"),
        rating = 4.9,
        reviewCount = 160,
        stock = 20,
        description = "Floor-length flared georgette gown with intricate sequin embroidery on bodice and sleeves.",
        isBestSeller = true,
        isRecommended = true
      ),
      Product(
        id = "p18",
        name = "Men's Slim Fit Stretch Jeans",
        category = "Men",
        subCategory = "Jeans",
        brand = "Royal Bengal Threads",
        sellerId = "s3",
        sellerName = "Rahim Uddin",
        shopName = "Bengal Men's Wear",
        shopLogo = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150",
        price = 2250.0,
        oldPrice = 2900.0,
        discountPercent = 22,
        images = listOf(
          "https://images.unsplash.com/photo-1542272604-787c3835535d?w=600",
          "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=600"
        ),
        colors = listOf("Dark Blue", "Washed Grey", "Jet Black"),
        sizes = listOf("30", "32", "34", "36", "38"),
        rating = 4.7,
        reviewCount = 184,
        stock = 50,
        description = "Comfortable cotton-elastane blend stretch denim designed for all-day flexibility and style.",
        isBestSeller = true
      ),
      Product(
        id = "p19",
        name = "Women's High-Waist Skinny Denim Jeans",
        category = "Women",
        subCategory = "Jeans",
        brand = "Dhaka Vogue",
        sellerId = "s2",
        sellerName = "Tanvir Ahmed",
        shopName = "Dhaka Vogue House",
        shopLogo = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150",
        price = 2100.0,
        oldPrice = 2700.0,
        discountPercent = 22,
        images = listOf(
          "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=600",
          "https://images.unsplash.com/photo-1582552938357-32b9f6740642?w=600"
        ),
        colors = listOf("Light Blue", "Indigo", "Black"),
        sizes = listOf("26", "28", "30", "32", "34"),
        rating = 4.6,
        reviewCount = 135,
        stock = 40,
        description = "Flattering high-rise skinny jeans with tummy control and premium stretch denim fabric.",
        isNewArrival = true
      ),
      Product(
        id = "p20",
        name = "Men's Graphic Oversized Cotton T-Shirt",
        category = "Men",
        subCategory = "T-shirts",
        brand = "Royal Bengal Threads",
        sellerId = "s3",
        sellerName = "Rahim Uddin",
        shopName = "Bengal Men's Wear",
        shopLogo = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150",
        price = 990.0,
        oldPrice = 1400.0,
        discountPercent = 29,
        images = listOf(
          "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600",
          "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=600"
        ),
        colors = listOf("Off-White", "Sage Green", "Charcoal"),
        sizes = listOf("S", "M", "L", "XL", "XXL"),
        rating = 4.8,
        reviewCount = 290,
        stock = 80,
        description = "Super combed 100% cotton 240 GSM streetwear oversized tee with minimalist chest print.",
        isBestSeller = true,
        isSpecialOffer = true
      )
    )
  )
  val products: StateFlow<List<Product>> = _products.asStateFlow()

  val categories = listOf(
    CategoryItem("c1", "Women", "woman", listOf("Dresses", "Tops", "Saree", "Salwar Kameez", "Jeans", "Shoes", "Bags"), "https://images.unsplash.com/photo-1483985988355-763728e1935b?w=400"),
    CategoryItem("c2", "Men", "man", listOf("Shirts", "T-shirts", "Pants", "Jeans", "Panjabi", "Shoes", "Watches"), "https://images.unsplash.com/photo-1490481651871-ab68de25d43d?w=400"),
    CategoryItem("c3", "Kids", "child", listOf("Boys", "Girls", "Baby"), "https://images.unsplash.com/photo-1503944583220-7f2679b360f4?w=400"),
    CategoryItem("c4", "Shoes", "footwear", listOf("Sneakers", "Heels", "Formal", "Sandals"), "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400"),
    CategoryItem("c5", "Bags", "bag", listOf("Tote", "Backpack", "Handbag", "Clutch"), "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=400"),
    CategoryItem("c6", "Accessories", "watch", listOf("Jewelry", "Sunglasses", "Belts", "Caps"), "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?w=400"),
    CategoryItem("c7", "Beauty", "face", listOf("Makeup", "Skincare", "Fragrance"), "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=400"),
    CategoryItem("c8", "Watches", "schedule", listOf("Chronos", "Smartwatch", "Luxury"), "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400")
  )

  private val _cartItems = MutableStateFlow<List<CartItem>>(
    mutableListOf(
      CartItem("ci1", _products.value[0], "Free Size", "Royal Blue", 1),
      CartItem("ci2", _products.value[2], "L", "Sky Blue", 2)
    )
  )
  val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

  private val _wishlistItems = MutableStateFlow<List<WishlistItem>>(
    mutableListOf(
      WishlistItem("wi1", _products.value[3]),
      WishlistItem("wi2", _products.value[6]),
      WishlistItem("wi3", _products.value[10])
    )
  )
  val wishlistItems: StateFlow<List<WishlistItem>> = _wishlistItems.asStateFlow()

  private val _addresses = MutableStateFlow<List<Address>>(
    mutableListOf(
      Address("a1", "Tanvir Hasan", "+880 1712 345678", "House 42, Road 11, Banani", "Dhaka", "1213", "Bangladesh", true),
      Address("a2", "Tanvir Hasan", "+880 1712 345678", "Flat 5B, Gulshan Avenue", "Dhaka", "1212", "Bangladesh", false)
    )
  )
  val addresses: StateFlow<List<Address>> = _addresses.asStateFlow()

  private val _orders = MutableStateFlow<List<Order>>(
    mutableListOf(
      Order(
        id = "ord-101",
        orderNumber = "ASF-849201",
        items = listOf(CartItem("oci1", _products.value[1], "M", "Pastel Pink", 1)),
        subtotal = 3200.0,
        discount = 320.0,
        deliveryFee = 60.0,
        totalAmount = 2940.0,
        address = Address("a1", "Tanvir Hasan", "+880 1712 345678", "House 42, Road 11, Banani", "Dhaka", "1213", "Bangladesh", true),
        paymentMethod = "bKash",
        status = OrderStatus.SHIPPED,
        estimatedDate = "Oct 2, 2026",
        createdAt = "Sep 26, 2026"
      )
    )
  )
  val orders: StateFlow<List<Order>> = _orders.asStateFlow()

  private val _user = MutableStateFlow(
    User(
      name = "Tanvir Hasan",
      email = "tanvir.hasan@gmail.com",
      phone = "+880 1712 345678",
      avatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150"
    )
  )
  val user: StateFlow<User> = _user.asStateFlow()

  private val _language = MutableStateFlow("en") // "en" or "bn"
  val language: StateFlow<String> = _language.asStateFlow()

  private val _currency = MutableStateFlow("BDT") // BDT, USD, GBP, EUR, AED, SAR, INR
  val currency: StateFlow<String> = _currency.asStateFlow()

  fun setLanguage(lang: String) {
    _language.value = lang
  }

  fun setCurrency(curr: String) {
    _currency.value = curr
  }

  fun formatPrice(amountInBdt: Double): String {
    val curr = _currency.value
    val converted = when (curr) {
      "USD" -> amountInBdt * 0.0085
      "GBP" -> amountInBdt * 0.0065
      "EUR" -> amountInBdt * 0.0078
      "AED" -> amountInBdt * 0.031
      "SAR" -> amountInBdt * 0.032
      "INR" -> amountInBdt * 0.71
      else -> amountInBdt
    }
    val symbol = when (curr) {
      "BDT" -> "৳"
      "USD" -> "$"
      "GBP" -> "£"
      "EUR" -> "€"
      "AED" -> "AED "
      "SAR" -> "SAR "
      "INR" -> "₹"
      else -> "৳"
    }
    return String.format("%s%.0f", symbol, converted)
  }

  // Cart operations
  fun addToCart(product: Product, size: String, color: String, quantity: Int = 1) {
    val current = _cartItems.value.toMutableList()
    val existing = current.find { it.product.id == product.id && it.selectedSize == size && it.selectedColor == color }
    if (existing != null) {
      existing.quantity += quantity
    } else {
      current.add(CartItem(id = "ci_${System.currentTimeMillis()}", product = product, selectedSize = size, selectedColor = color, quantity = quantity))
    }
    _cartItems.value = current
  }

  fun removeFromCart(cartItemId: String) {
    _cartItems.value = _cartItems.value.filter { it.id != cartItemId }
  }

  fun updateCartQuantity(cartItemId: String, delta: Int) {
    val current = _cartItems.value.map { item ->
      if (item.id == cartItemId) {
        val newQ = item.quantity + delta
        if (newQ > 0) item.copy(quantity = newQ) else item
      } else item
    }
    _cartItems.value = current
  }

  // Wishlist operations
  fun toggleWishlist(product: Product) {
    val current = _wishlistItems.value.toMutableList()
    val existing = current.find { it.product.id == product.id }
    if (existing != null) {
      current.remove(existing)
    } else {
      current.add(WishlistItem(id = "wi_${System.currentTimeMillis()}", product = product))
    }
    _wishlistItems.value = current
  }

  fun isProductInWishlist(productId: String): Boolean {
    return _wishlistItems.value.any { it.product.id == productId }
  }

  // Address operations
  fun addAddress(address: Address) {
    val current = _addresses.value.toMutableList()
    if (address.isDefault) {
      current.replaceAll { it.copy(isDefault = false) }
    }
    current.add(address)
    _addresses.value = current
  }

  // Order placement
  fun placeOrder(address: Address, paymentMethod: String, deliveryFee: Double): Order {
    val items = _cartItems.value
    val subtotal = items.sumOf { it.product.price * it.quantity }
    val discount = items.sumOf { (it.product.oldPrice - it.product.price) * it.quantity }.coerceAtLeast(0.0)
    val total = (subtotal + deliveryFee)

    val newOrder = Order(
      id = "ord_${System.currentTimeMillis()}",
      orderNumber = "ASF-${(100000..999999).random()}",
      items = items.toList(),
      subtotal = subtotal,
      discount = discount,
      deliveryFee = deliveryFee,
      totalAmount = total,
      address = address,
      paymentMethod = paymentMethod,
      status = OrderStatus.ORDER_CONFIRMED,
      estimatedDate = "3-5 Business Days",
      createdAt = "Today"
    )

    val currentOrders = _orders.value.toMutableList()
    currentOrders.add(0, newOrder)
    _orders.value = currentOrders

    // Clear cart after order
    _cartItems.value = emptyList()
    return newOrder
  }
}
