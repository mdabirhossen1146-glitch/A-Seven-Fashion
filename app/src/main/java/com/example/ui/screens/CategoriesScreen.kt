package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.ProductCard
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun CategoriesScreen(
  viewModel: FashionViewModel,
  onProductClick: (com.example.data.model.Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val categories = viewModel.categories
  val products by viewModel.products.collectAsState()
  val wishlist by viewModel.wishlistItems.collectAsState()

  var selectedCategoryName by remember { mutableStateOf<String?>(null) }
  var selectedSubCategory by remember { mutableStateOf<String?>(null) }

  val filteredProducts = products.filter { p ->
    val matchCat = selectedCategoryName == null || p.category.equals(selectedCategoryName, ignoreCase = true)
    val matchSub = selectedSubCategory == null || p.subCategory.equals(selectedSubCategory, ignoreCase = true)
    matchCat && matchSub
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(bottom = 80.dp)
  ) {
    // Top Bar
    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Categories",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    }

    if (selectedCategoryName == null) {
      LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(categories) { cat ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { selectedCategoryName = cat.name }
              .testTag("category_row_${cat.id}")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(60.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(MaterialTheme.colorScheme.surfaceVariant)
              ) {
                AsyncImage(
                  model = cat.imageUrl,
                  contentDescription = cat.name,
                  modifier = Modifier.fillMaxSize(),
                  contentScale = ContentScale.Crop
                )
              }
              Spacer(modifier = Modifier.width(16.dp))
              Column(
                modifier = Modifier.weight(1f)
              ) {
                Text(
                  text = cat.name,
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                  )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = cat.subCategories.joinToString(", "),
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                  ),
                  maxLines = 1
                )
              }
              Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    } else {
      val activeCatObj = categories.find { it.name.equals(selectedCategoryName, ignoreCase = true) }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          TextButton(onClick = {
            selectedCategoryName = null
            selectedSubCategory = null
          }) {
            Text(text = "← Back to Categories")
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "/ $selectedCategoryName",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
          )
        }

        if (activeCatObj != null && activeCatObj.subCategories.isNotEmpty()) {
          Spacer(modifier = Modifier.height(4.dp))
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            item {
              FilterChip(
                selected = selectedSubCategory == null,
                onClick = { selectedSubCategory = null },
                label = { Text("All") }
              )
            }
            items(activeCatObj.subCategories) { sub ->
              FilterChip(
                selected = selectedSubCategory == sub,
                onClick = { selectedSubCategory = sub },
                label = { Text(sub) }
              )
            }
          }
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
          Text(text = "No products found in this category.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      } else {
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          contentPadding = PaddingValues(16.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
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
}
