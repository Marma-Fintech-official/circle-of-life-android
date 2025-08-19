package com.example.circleoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun CreatePasswordScreen(navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Create Password",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("New Password") })
            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirm Password") })
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agree, onCheckedChange = { agree = it })
                Text("I understand that password cannot be recovered", color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("secureWallet") },
                enabled = password.length >= 8 && password == confirm && agree
            ) {
                Text("Create Password")
            }
        }
    }
}
