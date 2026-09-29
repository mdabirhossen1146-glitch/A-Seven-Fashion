package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CategoryItem

@Composable
fun CategoryCard(
  category: CategoryItem,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .width(80.dp)
      .clickable { onClick() }
      .testTag("category_card_${category.id}")
  ) {
    Box(
      modifier = Modifier
        .size(68.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
      AsyncImage(
        model = category.imageUrl,
        contentDescription = category.name,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = category.name,
      style = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurface
      ),
      textAlign = TextAlign.Center,
      maxLines = 1
    )
  }
}
