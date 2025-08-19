package com.example.circleoflife
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
@Composable
fun SecureWalletScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        color = Color.Black
    ){
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Secure Your Wallet", color = Color.White, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("Backup your Secret Recovery Phrase to avoid loss of access.", color = Color.White)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate("revealSecret") }) {
            Text("Start")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = { /* skip or warning */ }) {
            Text("Remind me later")
        }
    }
}}
