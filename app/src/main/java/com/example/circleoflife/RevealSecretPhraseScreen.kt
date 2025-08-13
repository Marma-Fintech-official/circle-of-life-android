package com.example.circleoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun RevealSecretPhraseScreen(navController: NavHostController) {
    var revealed by remember { mutableStateOf(false) }
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
                "Secret Recovery Phrase",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))

            if (revealed) {
                Text(
                    "1. local 2. riot 3. cost 4. fetch 5. scheme 6. size\n7. fatal 8. erosion 9. icon 10. boss 11. spy 12. ten",
                    color = Color.White
                )
            } else {
                Button(onClick = { revealed = true }) {
                    Text("View")
                }
            }

            Spacer(Modifier.height(16.dp))
            if (revealed) {
                Button(onClick = { navController.navigate("confirmSecret") }) {
                    Text("Continue")
                }
            }
        }
    }
}
