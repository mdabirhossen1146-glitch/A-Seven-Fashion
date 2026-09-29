package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.CategoryCard
import com.example.ui.components.ProductCard
import com.example.ui.components.SearchBar
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun HomeScreen(
  viewModel: FashionViewModel,
  onNavigateToSearch: () -> Unit,
  onNavigateToCategories: () -> Unit,
  onProductClick: (com.example.data.model.Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val products by viewModel.products.collectAsState()
  val categories = viewModel.categories
  val wishlist by viewModel.wishlistItems.collectAsState()

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
  ) {
    // Top Bar: Logo & Notification
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "A SEVEN FASHION",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.sp
          )
        )
        Text(
          text = "Fashion for Everyone",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }
      IconButton(
        onClick = { /* Notification action */ },
        modifier = Modifier.testTag("notification_btn")
      ) {
        Icon(
          imageVector = Icons.Default.Notifications,
          contentDescription = "Notifications",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    }

    // Search Bar
    Spacer(modifier = Modifier.height(8.dp))
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
      SearchBar(
        query = "",
        onQueryChange = {},
        onSearchClick = onNavigateToSearch,
        readOnly = true,
        onClick = onNavigateToSearch
      )
    }

    // Promotional Banner
    Spacer(modifier = Modifier.height(16.dp))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(170.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
      AsyncImage(
        model = "https://images.unsplash.com/photo-1445205170230-053b83016050?w=800",
        contentDescription = "Banner",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.8f
      )
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "NEW COLLECTION 2026",
          style = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Elegance & Style\nUp to 40% OFF",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
          onClick = onNavigateToSearch,
          shape = RoundedCornerShape(20.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier.height(36.dp).testTag("shop_now_banner_btn")
        ) {
          Text(text = "Shop Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Categories Shortcuts
    Spacer(modifier = Modifier.height(20.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Categories",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold
        )
      )
      TextButton(onClick = onNavigateToCategories) {
        Text(text = "See All", color = MaterialTheme.colorScheme.primary)
      }
    }
    Spacer(modifier = Modifier.height(4.dp))
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(categories) { category ->
        CategoryCard(
          category = category,
          onClick = onNavigateToCategories
        )
      }
    }

    // Best Selling Products
    Spacer(modifier = Modifier.height(24.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Best Selling Products",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold
        )
      )
    }
    Spacer(modifier = Modifier.height(10.dp))
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(products.filter { it.isBestSeller }) { product ->
        val isWishlisted = wishlist.any { it.product.id == product.id }
        ProductCard(
          product = product,
          isWishlisted = isWishlisted,
          onWishlistClick = { viewModel.toggleWishlist(product) },
          onAddToCartClick = { viewModel.addToCart(product, product.sizes.firstOrNull() ?: "M", product.colors.firstOrNull() ?: "Default") },
          onClick = { onProductClick(product) },
          formatPrice = { viewModel.formatPrice(it) }
        )
      }
    }

    // New Arrivals
    Spacer(modifier = Modifier.height(24.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "New Arrivals",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold
        )
      )
    }
    Spacer(modifier = Modifier.height(10.dp))
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(products.filter { it.isNewArrival }) { product ->
        val isWishlisted = wishlist.any { it.product.id == product.id }
        ProductCard(
          product = product,
          isWishlisted = isWishlisted,
          onWishlistClick = { viewModel.toggleWishlist(product) },
          onAddToCartClick = { viewModel.addToCart(product, product.sizes.firstOrNull() ?: "M", product.colors.firstOrNull() ?: "Default") },
          onClick = { onProductClick(product) },
          formatPrice = { viewModel.formatPrice(it) }
        )
      }
    }

    // Recommended For You
    Spacer(modifier = Modifier.height(24.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Recommended For You",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold
        )
      )
    }
    Spacer(modifier = Modifier.height(10.dp))
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(products.filter { it.isRecommended }) { product ->
        val isWishlisted = wishlist.any { it.product.id == product.id }
        ProductCard(
          product = product,
          isWishlisted = isWishlisted,
          onWishlistClick = { viewModel.toggleWishlist(product) },
          onAddToCartClick = { viewModel.addToCart(product, product.sizes.firstOrNull() ?: "M", product.colors.firstOrNull() ?: "Default") },
          onClick = { onProductClick(product) },
          formatPrice = { viewModel.formatPrice(it) }
        )
      }
    }

    Spacer(modifier = Modifier.height(80.dp))
  }
}
