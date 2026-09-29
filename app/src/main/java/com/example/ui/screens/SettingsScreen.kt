package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun SettingsScreen(
  viewModel: FashionViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val language by viewModel.language.collectAsState()
  val currency by viewModel.currency.collectAsState()

  val currencies = listOf("BDT", "USD", "GBP", "EUR", "AED", "SAR", "INR")
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
        IconButton(onClick = onBack, modifier = Modifier.testTag("settings_back_btn")) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
          text = "Language & Currency Settings",
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
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      Text(text = "App Language", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
      Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        FilterChip(
          selected = language == "en",
          onClick = { viewModel.setLanguage("en") },
          label = { Text("English") },
          modifier = Modifier.testTag("lang_en_chip")
        )
        FilterChip(
          selected = language == "bn",
          onClick = { viewModel.setLanguage("bn") },
          label = { Text("বাংলা (Bengali)") },
          modifier = Modifier.testTag("lang_bn_chip")
        )
      }

      Divider()

      Text(text = "Preferred Currency", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        currencies.forEach { curr ->
          Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (currency == curr) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { viewModel.setCurrency(curr) }
              .testTag("currency_chip_${curr.lowercase()}")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = curr, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
              if (currency == curr) {
                Surface(
                  color = MaterialTheme.colorScheme.primary,
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(text = "Active", color = androidx.compose.ui.graphics.Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
              }
            }
          }
        }
      }
    }
  }
}
