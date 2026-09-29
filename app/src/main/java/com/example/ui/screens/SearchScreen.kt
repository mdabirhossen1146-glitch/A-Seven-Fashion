package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ProductCard
import com.example.ui.components.SearchBar
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun SearchScreen(
  viewModel: FashionViewModel,
  onBack: () -> Unit,
  onProductClick: (com.example.data.model.Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val products by viewModel.products.collectAsState()
  val wishlist by viewModel.wishlistItems.collectAsState()

  var query by remember { mutableStateOf("") }
  var selectedSort by remember { mutableStateOf("Newest") }
  val sortOptions = listOf("Newest", "Price Low to High", "Price High to Low", "Highest Rated", "Discount", "Popular")

  val searchSuggestions = listOf("Shirts", "T-shirts", "Dresses", "Jeans", "Shoes", "Bags", "Watches", "Accessories", "Saree")

  val filteredProducts = products.filter { p ->
    if (query.isBlank()) true
    else p.name.contains(query, ignoreCase = true) ||
         p.category.contains(query, ignoreCase = true) ||
         p.subCategory.contains(query, ignoreCase = true) ||
         p.brand.contains(query, ignoreCase = true)
  }.let { list ->
    when (selectedSort) {
      "Price Low to High" -> list.sortedBy { it.price }
      "Price High to Low" -> list.sortedByDescending { it.price }
      "Highest Rated" -> list.sortedByDescending { it.rating }
      "Discount" -> list.sortedByDescending { it.discountPercent }
      "Popular" -> list.sortedByDescending { it.reviewCount }
      else -> list.reversed()
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("search_back_btn")) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Box(modifier = Modifier.weight(1f)) {
          SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearchClick = {},
            readOnly = false,
            placeholder = "Search fashion items, brands..."
          )
        }
      }
    }

    if (query.isBlank()) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Popular Searches", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(searchSuggestions) { suggestion ->
            SuggestionChip(
              onClick = { query = suggestion },
              label = { Text(suggestion) },
              modifier = Modifier.testTag("suggestion_${suggestion.lowercase()}")
            )
          }
        }
      }
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = "${filteredProducts.size} items found",
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )
    }

    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(sortOptions) { sort ->
        FilterChip(
          selected = selectedSort == sort,
          onClick = { selectedSort = sort },
          label = { Text(sort, fontSize = 12.sp) },
          modifier = Modifier.testTag("sort_chip_${sort.lowercase().replace(' ', '_')}")
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (filteredProducts.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "No matching products found.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    } else {
      LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(filteredProducts) { product ->
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
    }
  }
}
