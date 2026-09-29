package com.example.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingWidget(
  rating: Double,
  reviewCount: Int,
  starSize: androidx.compose.ui.unit.Dp = 14.dp,
  fontSize: androidx.compose.ui.unit.TextUnit = 12.sp
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.testTag("rating_widget")
  ) {
    Icon(
      imageVector = Icons.Default.Star,
      contentDescription = "Rating Star",
      tint = Color(0xFFF59E0B),
      modifier = Modifier.size(starSize)
    )
    Spacer(modifier = Modifier.width(3.dp))
    Text(
      text = "%.1f".format(rating),
      style = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onSurface
      )
    )
    if (reviewCount > 0) {
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "($reviewCount)",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = (fontSize.value * 0.9f).sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }
  }
}
