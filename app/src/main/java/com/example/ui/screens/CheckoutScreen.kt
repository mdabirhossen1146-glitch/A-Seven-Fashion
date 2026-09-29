package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.ui.components.AddressCard
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun CheckoutScreen(
  viewModel: FashionViewModel,
  onBack: () -> Unit,
  onOrderPlaced: (com.example.data.model.Order) -> Unit,
  modifier: Modifier = Modifier
) {
  val addresses by viewModel.addresses.collectAsState()
  val cartItems by viewModel.cartItems.collectAsState()

  var selectedAddressId by remember { mutableStateOf(addresses.firstOrNull()?.id ?: "") }
  var deliveryMethod by remember { mutableStateOf("Standard") }
  var paymentMethod by remember { mutableStateOf("Cash on Delivery") }

  val subtotal = cartItems.sumOf { it.product.price * it.quantity }
  val deliveryFee = if (deliveryMethod == "Express") 150.0 else 60.0
  val total = subtotal + deliveryFee

  val scrollState = rememberScrollState()

  Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(bottom = 90.dp)
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
            .padding(horizontal = 8.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = onBack, modifier = Modifier.testTag("checkout_back_btn")) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
          }
          Text(
            text = "Checkout",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold
            )
          )
        }
      }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        Text(text = "1. Delivery Address", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          addresses.forEach { addr ->
            AddressCard(
              address = addr,
              isSelected = addr.id == selectedAddressId,
              onSelect = { selectedAddressId = addr.id }
            )
          }
        }

        Text(text = "2. Delivery Method", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          DeliveryMethodCard(
            title = "Standard Delivery",
            time = "3-5 Business Days",
            price = viewModel.formatPrice(60.0),
            isSelected = deliveryMethod == "Standard",
            onClick = { deliveryMethod = "Standard" },
            modifier = Modifier.weight(1f)
          )
          DeliveryMethodCard(
            title = "Express Delivery",
            time = "1-2 Business Days",
            price = viewModel.formatPrice(150.0),
            isSelected = deliveryMethod == "Express",
            onClick = { deliveryMethod = "Express" },
            modifier = Modifier.weight(1f)
          )
        }

        Text(text = "3. Payment Method", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        val paymentOptions = listOf("Cash on Delivery", "bKash", "Nagad", "Card", "PayPal")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          paymentOptions.forEach { method ->
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (paymentMethod == method) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
              ),
              modifier = Modifier
                .fillMaxWidth()
                .clickable { paymentMethod = method }
                .testTag("payment_method_${method.lowercase().replace(' ', '_')}")
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = method, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                if (paymentMethod == method) {
                  Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order Summary", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = "Items Subtotal", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(text = viewModel.formatPrice(subtotal), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = "Delivery Fee", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(text = viewModel.formatPrice(deliveryFee), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = "Total Payable", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
              Text(text = viewModel.formatPrice(total), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
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
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Button(
          onClick = {
            val address = addresses.find { it.id == selectedAddressId } ?: addresses.first()
            val order = viewModel.placeOrder(address, paymentMethod, deliveryFee)
            onOrderPlaced(order)
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("place_order_btn")
        ) {
          Text(text = "Place Order (${viewModel.formatPrice(total)})", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun DeliveryMethodCard(
  title: String,
  time: String,
  price: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface),
    modifier = modifier
      .clickable { onClick() }
      .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Spacer(modifier = Modifier.height(6.dp))
      Text(text = price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
    }
  }
}
