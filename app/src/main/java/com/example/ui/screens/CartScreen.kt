package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CartItemRow
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun CartScreen(
  viewModel: FashionViewModel,
  onProceedToCheckout: () -> Unit,
  modifier: Modifier = Modifier
) {
  val cartItems by viewModel.cartItems.collectAsState()

  val subtotal = cartItems.sumOf { it.product.price * it.quantity }
  val discount = cartItems.sumOf { (it.product.oldPrice - it.product.price) * it.quantity }.coerceAtLeast(0.0)
  val deliveryFee = if (cartItems.isNotEmpty()) 60.0 else 0.0
  val total = (subtotal + deliveryFee)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
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
          text = "Shopping Cart (${cartItems.size})",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    }

    if (cartItems.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(32.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Outlined.ShoppingBag,
            contentDescription = "Empty Cart",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(72.dp)
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Your Cart is Empty",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Explore our latest fashion collections and add items to your cart.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            textAlign = TextAlign.Center
          )
        }
      }
    } else {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 80.dp)
      ) {
        LazyColumn(
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.weight(1f)
        ) {
          items(cartItems) { item ->
            CartItemRow(
              cartItem = item,
              onQuantityChange = { delta -> viewModel.updateCartQuantity(item.id, delta) },
              onRemove = { viewModel.removeFromCart(item.id) },
              formatPrice = { viewModel.formatPrice(it) }
            )
          }
        }

        // Summary Card
        Surface(
          color = MaterialTheme.colorScheme.surface,
          tonalElevation = 8.dp,
          shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Subtotal", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(text = viewModel.formatPrice(subtotal), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(6.dp))
            if (discount > 0) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "Discount Savings", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                Text(text = "-${viewModel.formatPrice(discount)}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
              }
              Spacer(modifier = Modifier.height(6.dp))
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Delivery Fee", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(text = viewModel.formatPrice(deliveryFee), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Divider(modifier = Modifier.padding(vertical = 10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Total Amount", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              Text(text = viewModel.formatPrice(total), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onProceedToCheckout,
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("proceed_checkout_btn")
            ) {
              Text(text = "Proceed to Checkout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
