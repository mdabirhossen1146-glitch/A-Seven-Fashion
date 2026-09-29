package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
fun AddressScreen(
  viewModel: FashionViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val addresses by viewModel.addresses.collectAsState()
  var showAddDialog by remember { mutableStateOf(false) }

  var fullName by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var addressLine by remember { mutableStateOf("") }
  var city by remember { mutableStateOf("Dhaka") }
  var postalCode by remember { mutableStateOf("") }
  var country by remember { mutableStateOf("Bangladesh") }

  Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    Column(modifier = Modifier.fillMaxSize()) {
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
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.testTag("address_back_btn")) {
              Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
              text = "My Addresses (${addresses.size})",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
              )
            )
          }
          IconButton(onClick = { showAddDialog = true }, modifier = Modifier.testTag("add_address_btn")) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Address", tint = MaterialTheme.colorScheme.primary)
          }
        }
      }

      LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(addresses) { addr ->
          AddressCard(
            address = addr,
            isSelected = addr.isDefault,
            onSelect = {}
          )
        }
      }
    }

    if (showAddDialog) {
      AlertDialog(
        onDismissRequest = { showAddDialog = false },
        title = { Text("Add New Delivery Address") },
        text = {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = addressLine, onValueChange = { addressLine = it }, label = { Text("House / Street Address") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = postalCode, onValueChange = { postalCode = it }, label = { Text("Postal Code") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = country, onValueChange = { country = it }, label = { Text("Country") }, modifier = Modifier.fillMaxWidth())
          }
        },
        confirmButton = {
          Button(
            onClick = {
              if (fullName.isNotBlank() && phone.isNotBlank() && addressLine.isNotBlank()) {
                val newAddr = Address(
                  id = "addr_${System.currentTimeMillis()}",
                  fullName = fullName,
                  phone = phone,
                  addressLine = addressLine,
                  city = city,
                  postalCode = postalCode,
                  country = country,
                  isDefault = addresses.isEmpty()
                )
                viewModel.addAddress(newAddr)
                showAddDialog = false
                fullName = ""; phone = ""; addressLine = ""; postalCode = ""
              }
            }
          ) {
            Text("Save Address")
          }
        },
        dismissButton = {
          TextButton(onClick = { showAddDialog = false }) {
            Text("Cancel")
          }
        }
      )
    }
  }
}
