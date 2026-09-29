package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Product

@Composable
fun ProductCard(
  product: Product,
  isWishlisted: Boolean,
  onWishlistClick: () -> Unit,
  onAddToCartClick: () -> Unit,
  onClick: () -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .width(170.dp)
      .clickable { onClick() }
      .testTag("product_card_${product.id}")
  ) {
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        AsyncImage(
          model = product.images.firstOrNull() ?: "",
          contentDescription = product.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Discount tag
        if (product.discountPercent > 0) {
          Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 12.dp),
            modifier = Modifier.align(Alignment.TopStart)
          ) {
            Text(
              text = "${product.discountPercent}% OFF",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = Color.White
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        // Wishlist heart button
        IconButton(
          onClick = onWishlistClick,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(4.dp)
            .size(36.dp)
            .background(Color.White.copy(alpha = 0.85f), CircleShape)
            .testTag("wishlist_btn_${product.id}")
        ) {
          Icon(
            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Wishlist",
            tint = if (isWishlisted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(10.dp)
      ) {
        Text(
          text = product.shopName,
          style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 10.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = product.name,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface
          ),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.height(36.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        RatingWidget(rating = product.rating, reviewCount = product.reviewCount)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          PriceDisplay(
            currentPriceFormatted = formatPrice(product.price),
            oldPriceFormatted = formatPrice(product.oldPrice),
            discountPercent = 0,
            currentPriceSize = 14.sp,
            showDiscountBadge = false
          )
          
          IconButton(
            onClick = onAddToCartClick,
            modifier = Modifier
              .size(32.dp)
              .background(MaterialTheme.colorScheme.primary, CircleShape)
              .testTag("add_to_cart_btn_${product.id}")
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Add to Cart",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
