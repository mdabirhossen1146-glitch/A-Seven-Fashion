package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HelpSupportScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
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
        IconButton(onClick = onBack, modifier = Modifier.testTag("help_back_btn")) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
          text = "Help & Support",
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
        .padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(text = "Customer Care Hotline", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text(text = "+880 9612 345678 (9:00 AM - 10:00 PM)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Email Support", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
          Text(text = "support@asevenfashion.com", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Headquarters", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
          Text(text = "Gulshan Avenue, Dhaka-1212, Bangladesh", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    }
  }
}
