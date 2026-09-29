package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.example.data.model.Product
import com.example.ui.components.PriceDisplay
import com.example.ui.components.ProductCard
import com.example.ui.components.RatingWidget
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun ProductDetailScreen(
  viewModel: FashionViewModel,
  product: Product,
  onBack: () -> Unit,
  onAddToCart: () -> Unit,
  onBuyNow: () -> Unit,
  onProductClick: (Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val wishlist by viewModel.wishlistItems.collectAsState()
  val allProducts by viewModel.products.collectAsState()

  val isWishlisted = wishlist.any { it.product.id == product.id }

  var selectedImageIndex by remember { mutableStateOf(0) }
  var selectedSize by remember { mutableStateOf(product.sizes.firstOrNull() ?: "M") }
  var selectedColor by remember { mutableStateOf(product.colors.firstOrNull() ?: "Default") }
  var quantity by remember { mutableStateOf(1) }

  val similarProducts = allProducts.filter { it.category == product.category && it.id != product.id }
  val scrollState = rememberScrollState()

  Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(bottom = 90.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(360.dp)
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        AsyncImage(
          model = product.images.getOrNull(selectedImageIndex) ?: product.images.first(),
          contentDescription = product.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        IconButton(
          onClick = onBack,
          modifier = Modifier
            .padding(16.dp)
            .size(40.dp)
            .background(Color.White.copy(alpha = 0.85f), CircleShape)
            .align(Alignment.TopStart)
            .testTag("back_btn")
        ) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        IconButton(
          onClick = { viewModel.toggleWishlist(product) },
          modifier = Modifier
            .padding(16.dp)
            .size(40.dp)
            .background(Color.White.copy(alpha = 0.85f), CircleShape)
            .align(Alignment.TopEnd)
            .testTag("detail_wishlist_btn")
        ) {
          Icon(
            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Wishlist",
            tint = if (isWishlisted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
          )
        }

        if (product.images.size > 1) {
          Row(
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            product.images.forEachIndexed { index, imgUrl ->
              Box(
                modifier = Modifier
                  .size(50.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .border(
                    width = if (selectedImageIndex == index) 2.dp else 0.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                  )
                  .clickable { selectedImageIndex = index }
              ) {
                AsyncImage(model = imgUrl, contentDescription = "Thumb", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
              }
            }
          }
        }
      }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = product.brand,
            style = MaterialTheme.typography.labelMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold
            )
          )
          Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = if (product.stock > 0) "In Stock (${product.stock})" else "Out of Stock",
              style = MaterialTheme.typography.labelSmall.copy(
                color = if (product.stock > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = product.name,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        RatingWidget(rating = product.rating, reviewCount = product.reviewCount, starSize = 16.dp, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

        PriceDisplay(
          currentPriceFormatted = viewModel.formatPrice(product.price),
          oldPriceFormatted = viewModel.formatPrice(product.oldPrice),
          discountPercent = product.discountPercent,
          currentPriceSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        if (product.colors.isNotEmpty()) {
          Text(text = "Select Color: $selectedColor", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.colors.forEach { colorName ->
              FilterChip(
                selected = selectedColor == colorName,
                onClick = { selectedColor = colorName },
                label = { Text(colorName) }
              )
            }
          }
          Spacer(modifier = Modifier.height(16.dp))
        }

        if (product.sizes.isNotEmpty()) {
          Text(text = "Select Size: $selectedSize", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            product.sizes.forEach { sizeName ->
              FilterChip(
                selected = selectedSize == sizeName,
                onClick = { selectedSize = sizeName },
                label = { Text(sizeName) }
              )
            }
          }
          Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(text = "Quantity", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
          ) {
            IconButton(onClick = { if (quantity > 1) quantity-- }) {
              Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text(text = "$quantity", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp))
            IconButton(onClick = { if (quantity < product.stock) quantity++ }) {
              Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Product Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = product.description,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp
          )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape)) {
              AsyncImage(model = product.shopLogo, contentDescription = "Shop", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = product.shopName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
              Text(text = "Seller: ${product.sellerName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }

        if (similarProducts.isNotEmpty()) {
          Spacer(modifier = Modifier.height(24.dp))
          Text(text = "Similar Products", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(10.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(similarProducts) { sim ->
              ProductCard(
                product = sim,
                isWishlisted = wishlist.any { it.product.id == sim.id },
                onWishlistClick = { viewModel.toggleWishlist(sim) },
                onAddToCartClick = { viewModel.addToCart(sim, sim.sizes.firstOrNull() ?: "M", sim.colors.firstOrNull() ?: "Default") },
                onClick = { onProductClick(sim) },
                formatPrice = { viewModel.formatPrice(it) }
              )
            }
          }
        }
      }
    }

    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 12.dp,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = {
            viewModel.addToCart(product, selectedSize, selectedColor, quantity)
            onAddToCart()
          },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("add_to_cart_detail_btn")
        ) {
          Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = "Cart", modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Add to Cart", fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = {
            viewModel.addToCart(product, selectedSize, selectedColor, quantity)
            onBuyNow()
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("buy_now_btn")
        ) {
          Text(text = "Buy Now", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
