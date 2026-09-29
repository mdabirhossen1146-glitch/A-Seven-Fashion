package com.example.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PriceDisplay(
  currentPriceFormatted: String,
  oldPriceFormatted: String,
  discountPercent: Int,
  currentPriceSize: androidx.compose.ui.unit.TextUnit = 16.sp,
  showDiscountBadge: Boolean = true
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = currentPriceFormatted,
      style = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = currentPriceSize,
        color = MaterialTheme.colorScheme.primary
      ),
      modifier = Modifier.testTag("price_current")
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = oldPriceFormatted,
      style = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textDecoration = TextDecoration.LineThrough,
        fontSize = (currentPriceSize.value * 0.8f).sp
      ),
      modifier = Modifier.testTag("price_old")
    )
    if (showDiscountBadge && discountPercent > 0) {
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "-$discountPercent%",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.testTag("discount_badge")
      )
    }
  }
}
