package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

sealed class Screen(val route: String, val title: String, val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector, val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector) {
  object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
  object Categories : Screen("categories", "Categories", Icons.Filled.Category, Icons.Outlined.Category)
  object Wishlist : Screen("wishlist", "Wishlist", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
  object Cart : Screen("cart", "Cart", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag)
  object Profile : Screen("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun AppBottomBar(
  currentRoute: String,
  onItemClick: (Screen) -> Unit,
  cartBadgeCount: Int = 0,
  wishlistBadgeCount: Int = 0
) {
  val items = listOf(
    Screen.Home,
    Screen.Categories,
    Screen.Wishlist,
    Screen.Cart,
    Screen.Profile
  )

  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp
  ) {
    items.forEach { screen ->
      val selected = currentRoute == screen.route
      NavigationBarItem(
        icon = {
          BadgedBox(
            badge = {
              if (screen == Screen.Cart && cartBadgeCount > 0) {
                Badge { Text("$cartBadgeCount") }
              } else if (screen == Screen.Wishlist && wishlistBadgeCount > 0) {
                Badge { Text("$wishlistBadgeCount") }
              }
            }
          ) {
            Icon(
              imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
              contentDescription = screen.title,
              modifier = Modifier.size(24.dp)
            )
          }
        },
        label = { Text(screen.title) },
        selected = selected,
        onClick = { onItemClick(screen) },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
          indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        ),
        modifier = Modifier.testTag("nav_item_${screen.route}")
      )
    }
  }
}
