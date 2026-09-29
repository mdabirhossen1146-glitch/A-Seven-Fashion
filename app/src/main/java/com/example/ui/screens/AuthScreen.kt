package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.FashionViewModel

@Composable
fun AuthScreen(
  viewModel: FashionViewModel,
  onLoginSuccess: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isLoginMode by remember { mutableStateOf(true) }

  var emailOrPhone by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var name by remember { mutableStateOf("") }
  var confirmPassword by remember { mutableStateOf("") }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(40.dp))
    Text(
      text = "A SEVEN FASHION",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        letterSpacing = 1.2.sp
      )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Fashion for Everyone",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    )

    Spacer(modifier = Modifier.height(30.dp))

    // Tab selector
    TabRow(
      selectedTabIndex = if (isLoginMode) 0 else 1,
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
    ) {
      Tab(
        selected = isLoginMode,
        onClick = { isLoginMode = true },
        text = { Text("Login", fontWeight = FontWeight.Bold) },
        modifier = Modifier.testTag("login_tab")
      )
      Tab(
        selected = !isLoginMode,
        onClick = { isLoginMode = false },
        text = { Text("Register", fontWeight = FontWeight.Bold) },
        modifier = Modifier.testTag("register_tab")
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        if (!isLoginMode) {
          OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth().testTag("reg_name_input")
          )
        }

        OutlinedTextField(
          value = emailOrPhone,
          onValueChange = { emailOrPhone = it },
          label = { Text(if (isLoginMode) "Email or Phone" else "Email") },
          modifier = Modifier.fillMaxWidth().testTag("email_input")
        )

        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Password") },
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier.fillMaxWidth().testTag("password_input")
        )

        if (!isLoginMode) {
          OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().testTag("confirm_password_input")
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
          onClick = onLoginSuccess,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("auth_submit_btn")
        ) {
          Text(text = if (isLoginMode) "Login" else "Create Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        if (isLoginMode) {
          TextButton(
            onClick = { /* Forgot password action */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
          ) {
            Text(text = "Forgot Password?", color = MaterialTheme.colorScheme.primary)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Text(
      text = "Ready for Firebase Authentication Integration",
      style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
      textAlign = TextAlign.Center
    )
  }
}
