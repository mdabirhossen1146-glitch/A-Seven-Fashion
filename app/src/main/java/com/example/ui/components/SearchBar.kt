package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
  query: String,
  onQueryChange: (String) -> Unit,
  onSearchClick: () -> Unit,
  placeholder: String = "Search shirts, sarees, shoes, brands...",
  modifier: Modifier = Modifier,
  readOnly: Boolean = false,
  onClick: (() -> Unit)? = null
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(52.dp)
      .clip(RoundedCornerShape(26.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
      .then(
        if (onClick != null) Modifier.clickable { onClick() } else Modifier
      )
      .testTag("search_bar")
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.width(12.dp))
      if (readOnly || onClick != null) {
        Text(
          text = if (query.isEmpty()) placeholder else query,
          style = MaterialTheme.typography.bodyMedium,
          color = if (query.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.weight(1.8f)
        )
      } else {
        TextField(
          value = query,
          onValueChange = onQueryChange,
          placeholder = { Text(placeholder, style = MaterialTheme.typography.bodyMedium) },
          colors = TextFieldDefaults.colors(
            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
          ),
          textStyle = MaterialTheme.typography.bodyMedium,
          modifier = Modifier
            .weight(1.8f)
            .testTag("search_input")
        )
      }
    }
  }
}
