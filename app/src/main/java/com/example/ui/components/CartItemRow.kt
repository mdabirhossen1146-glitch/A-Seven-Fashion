package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CartItem

@Composable
fun CartItemRow(
  cartItem: CartItem,
  onQuantityChange: (Int) -> Unit,
  onRemove: () -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("cart_item_${cartItem.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        AsyncImage(
          model = cartItem.product.images.firstOrNull() ?: "",
          contentDescription = cartItem.product.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = cartItem.product.name,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Size: ${cartItem.selectedSize} | Color: ${cartItem.selectedColor}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        PriceDisplay(
          currentPriceFormatted = formatPrice(cartItem.product.price),
          oldPriceFormatted = formatPrice(cartItem.product.oldPrice),
          discountPercent = cartItem.product.discountPercent,
          currentPriceSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(horizontal = 4.dp, vertical = 2.dp)
          ) {
            IconButton(
              onClick = { onQuantityChange(-1) },
              modifier = Modifier.size(28.dp).testTag("qty_minus_${cartItem.id}")
            ) {
              Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(14.dp))
            }
            Text(
              text = "${cartItem.quantity}",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(
              onClick = { onQuantityChange(1) },
              modifier = Modifier.size(28.dp).testTag("qty_plus_${cartItem.id}")
            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(14.dp))
            }
          }

          IconButton(
            onClick = onRemove,
            modifier = Modifier.size(32.dp).testTag("remove_item_${cartItem.id}")
          ) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Remove",
              tint = MaterialTheme.colorScheme.error,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}
