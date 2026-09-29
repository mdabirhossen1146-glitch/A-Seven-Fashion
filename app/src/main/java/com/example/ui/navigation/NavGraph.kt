package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.model.Order
import com.example.data.model.Product
import com.example.ui.components.AppBottomBar
import com.example.ui.components.Screen
import com.example.ui.screens.*
import com.example.ui.viewmodel.FashionViewModel

sealed class AppRoute(val route: String) {
  object MainNav : AppRoute("main_nav")
  object Search : AppRoute("search")
  object ProductDetail : AppRoute("product_detail")
  object Checkout : AppRoute("checkout")
  object OrderConfirmation : AppRoute("order_confirmation")
  object OrderTracking : AppRoute("order_tracking")
  object Orders : AppRoute("orders")
  object Addresses : AppRoute("addresses")
  object Auth : AppRoute("auth")
  object Settings : AppRoute("settings")
  object Help : AppRoute("help")
  object Legal : AppRoute("legal")
}

@Composable
fun AppNavGraph(viewModel: FashionViewModel) {
  var currentRoute by remember { mutableStateOf<String>(AppRoute.MainNav.route) }
  var mainTabRoute by remember { mutableStateOf(Screen.Home.route) }
  var selectedProduct by remember { mutableStateOf<Product?>(null) }
  var placedOrder by remember { mutableStateOf<Order?>(null) }
  var trackingOrder by remember { mutableStateOf<Order?>(null) }
  var legalTitle by remember { mutableStateOf("Privacy Policy") }

  val cartItems by viewModel.cartItems.collectAsState()
  val wishlistItems by viewModel.wishlistItems.collectAsState()

  val showBottomBar = currentRoute == AppRoute.MainNav.route

  Scaffold(
    bottomBar = {
      if (showBottomBar) {
        AppBottomBar(
          currentRoute = mainTabRoute,
          onItemClick = { screen ->
            mainTabRoute = screen.route
          },
          cartBadgeCount = cartItems.sumOf { it.quantity },
          wishlistBadgeCount = wishlistItems.size
        )
      }
    }
  ) { innerPadding ->
    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
      when (currentRoute) {
        AppRoute.MainNav.route -> {
          when (mainTabRoute) {
            Screen.Home.route -> HomeScreen(
              viewModel = viewModel,
              onNavigateToSearch = { currentRoute = AppRoute.Search.route },
              onNavigateToCategories = { mainTabRoute = Screen.Categories.route },
              onProductClick = { prod ->
                selectedProduct = prod
                currentRoute = AppRoute.ProductDetail.route
              }
            )
            Screen.Categories.route -> CategoriesScreen(
              viewModel = viewModel,
              onProductClick = { prod ->
                selectedProduct = prod
                currentRoute = AppRoute.ProductDetail.route
              }
            )
            Screen.Wishlist.route -> WishlistScreen(
              viewModel = viewModel,
              onProductClick = { prod ->
                selectedProduct = prod
                currentRoute = AppRoute.ProductDetail.route
              }
            )
            Screen.Cart.route -> CartScreen(
              viewModel = viewModel,
              onProceedToCheckout = { currentRoute = AppRoute.Checkout.route }
            )
            Screen.Profile.route -> ProfileScreen(
              viewModel = viewModel,
              onNavigateToOrders = { currentRoute = AppRoute.Orders.route },
              onNavigateToWishlist = { mainTabRoute = Screen.Wishlist.route },
              onNavigateToAddresses = { currentRoute = AppRoute.Addresses.route },
              onNavigateToSettings = { currentRoute = AppRoute.Settings.route },
              onNavigateToHelp = { currentRoute = AppRoute.Help.route },
              onNavigateToLegal = { title ->
                legalTitle = title
                currentRoute = AppRoute.Legal.route
              },
              onLogout = { currentRoute = AppRoute.Auth.route }
            )
          }
        }

        AppRoute.Search.route -> {
          BackHandler { currentRoute = AppRoute.MainNav.route }
          SearchScreen(
            viewModel = viewModel,
            onBack = { currentRoute = AppRoute.MainNav.route },
            onProductClick = { prod ->
              selectedProduct = prod
              currentRoute = AppRoute.ProductDetail.route
            }
          )
        }

        AppRoute.ProductDetail.route -> {
          BackHandler { currentRoute = AppRoute.MainNav.route }
          if (selectedProduct != null) {
            ProductDetailScreen(
              viewModel = viewModel,
              product = selectedProduct!!,
              onBack = { currentRoute = AppRoute.MainNav.route },
              onAddToCart = { mainTabRoute = Screen.Cart.route; currentRoute = AppRoute.MainNav.route },
              onBuyNow = { currentRoute = AppRoute.Checkout.route },
              onProductClick = { prod -> selectedProduct = prod }
            )
          }
        }

        AppRoute.Checkout.route -> {
          BackHandler { currentRoute = AppRoute.MainNav.route }
          CheckoutScreen(
            viewModel = viewModel,
            onBack = { currentRoute = AppRoute.MainNav.route },
            onOrderPlaced = { order ->
              placedOrder = order
              currentRoute = AppRoute.OrderConfirmation.route
            }
          )
        }

        AppRoute.OrderConfirmation.route -> {
          BackHandler { currentRoute = AppRoute.MainNav.route }
          if (placedOrder != null) {
            OrderConfirmationScreen(
              viewModel = viewModel,
              order = placedOrder!!,
              onTrackOrder = { order ->
                trackingOrder = order
                currentRoute = AppRoute.OrderTracking.route
              },
              onContinueShopping = {
                mainTabRoute = Screen.Home.route
                currentRoute = AppRoute.MainNav.route
              }
            )
          }
        }

        AppRoute.OrderTracking.route -> {
          BackHandler { currentRoute = AppRoute.Orders.route }
          if (trackingOrder != null) {
            OrderTrackingScreen(
              viewModel = viewModel,
              order = trackingOrder!!,
              onBack = { currentRoute = AppRoute.Orders.route }
            )
          }
        }

        AppRoute.Orders.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          OrdersScreen(
            viewModel = viewModel,
            onBack = { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route },
            onTrackOrder = { order ->
              trackingOrder = order
              currentRoute = AppRoute.OrderTracking.route
            }
          )
        }

        AppRoute.Addresses.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          AddressScreen(
            viewModel = viewModel,
            onBack = { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          )
        }

        AppRoute.Auth.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          AuthScreen(
            viewModel = viewModel,
            onLoginSuccess = { mainTabRoute = Screen.Home.route; currentRoute = AppRoute.MainNav.route }
          )
        }

        AppRoute.Settings.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          SettingsScreen(
            viewModel = viewModel,
            onBack = { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          )
        }

        AppRoute.Help.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          HelpSupportScreen(
            onBack = { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          )
        }

        AppRoute.Legal.route -> {
          BackHandler { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          LegalScreen(
            title = legalTitle,
            onBack = { mainTabRoute = Screen.Profile.route; currentRoute = AppRoute.MainNav.route }
          )
        }
      }
    }
  }
}
