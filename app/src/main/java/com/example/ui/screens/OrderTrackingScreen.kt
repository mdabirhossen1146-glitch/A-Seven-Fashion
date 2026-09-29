package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun OrderTrackingScreen(
  viewModel: FashionViewModel,
  order: Order,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val statuses = listOf(
    OrderStatus.ORDER_CONFIRMED to "Order Confirmed",
    OrderStatus.PROCESSING to "Processing",
    OrderStatus.PACKED to "Packed",
    OrderStatus.SHIPPED to "Shipped",
    OrderStatus.OUT_FOR_DELIVERY to "Out for Delivery",
    OrderStatus.DELIVERED to "Delivered"
  )

  val currentIndex = statuses.indexOfFirst { it.first == order.status }.let { if (it < 0) 0 else it }
  val scrollState = rememberScrollState()

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
        IconButton(onClick = onBack, modifier = Modifier.testTag("tracking_back_btn")) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
          text = "Track Order #${order.orderNumber}",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
          )
        )
      }
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
        .padding(20.dp)
    ) {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(text = "Order Status Timeline", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(16.dp))

          statuses.forEachIndexed { index, (st, label) ->
            val isCompleted = index <= currentIndex
            val isCurrent = index == currentIndex

            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(
                    if (isCompleted) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                  ),
                contentAlignment = Alignment.Center
              ) {
                if (isCompleted) {
                  Icon(imageVector = Icons.Default.Check, contentDescription = "Done", tint = Color.White, modifier = Modifier.size(16.dp))
                } else {
                  Text(text = "${index + 1}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
              }
              Spacer(modifier = Modifier.width(16.dp))
              Column {
                Text(
                  text = label,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCompleted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
                if (isCurrent) {
                  Text(text = "Current Status", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(text = "Order Details", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
          Text(text = "Payment: ${order.paymentMethod}", style = MaterialTheme.typography.bodyMedium)
          Text(text = "Estimated Delivery: ${order.estimatedDate}", style = MaterialTheme.typography.bodyMedium)
          Text(text = "Total: ${viewModel.formatPrice(order.totalAmount)}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
      }
    }
  }
}
