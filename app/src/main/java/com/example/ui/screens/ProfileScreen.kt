package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun ProfileScreen(
  viewModel: FashionViewModel,
  onNavigateToOrders: () -> Unit,
  onNavigateToWishlist: () -> Unit,
  onNavigateToAddresses: () -> Unit,
  onNavigateToSettings: () -> Unit,
  onNavigateToHelp: () -> Unit,
  onNavigateToLegal: (String) -> Unit,
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  val user by viewModel.user.collectAsState()
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(bottom = 80.dp)
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
          AsyncImage(
            model = user.avatarUrl,
            contentDescription = user.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = user.name,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = user.email,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Text(
          text = user.phone,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      ProfileMenuItem(icon = Icons.Default.ShoppingBag, title = "My Orders", onClick = onNavigateToOrders, testTag = "menu_orders")
      ProfileMenuItem(icon = Icons.Default.Favorite, title = "Wishlist", onClick = onNavigateToWishlist, testTag = "menu_wishlist")
      ProfileMenuItem(icon = Icons.Default.LocationOn, title = "My Addresses", onClick = onNavigateToAddresses, testTag = "menu_addresses")
      ProfileMenuItem(icon = Icons.Default.Payment, title = "Payment Methods", onClick = onNavigateToSettings, testTag = "menu_payments")
      ProfileMenuItem(icon = Icons.Default.Notifications, title = "Notifications", onClick = onNavigateToSettings, testTag = "menu_notifications")
      ProfileMenuItem(icon = Icons.Default.Star, title = "My Reviews", onClick = onNavigateToSettings, testTag = "menu_reviews")
      ProfileMenuItem(icon = Icons.Default.Language, title = "Language & Currency", onClick = onNavigateToSettings, testTag = "menu_language")
      ProfileMenuItem(icon = Icons.Default.Settings, title = "Settings", onClick = onNavigateToSettings, testTag = "menu_settings")
      ProfileMenuItem(icon = Icons.Default.Help, title = "Help & Support", onClick = onNavigateToHelp, testTag = "menu_help")
      ProfileMenuItem(icon = Icons.Default.PrivacyTip, title = "Privacy Policy", onClick = { onNavigateToLegal("Privacy Policy") }, testTag = "menu_privacy")
      ProfileMenuItem(icon = Icons.Default.Description, title = "Terms & Conditions", onClick = { onNavigateToLegal("Terms & Conditions") }, testTag = "menu_terms")
      
      Spacer(modifier = Modifier.height(8.dp))
      
      Button(
        onClick = onLogout,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("logout_btn")
      ) {
        Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Logout", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }
    }
  }
}

@Composable
fun ProfileMenuItem(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  onClick: () -> Unit,
  testTag: String
) {
  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(22.dp)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Medium,
          fontSize = 15.sp
        ),
        modifier = Modifier.weight(1f)
      )
      Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = "Open",
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}
