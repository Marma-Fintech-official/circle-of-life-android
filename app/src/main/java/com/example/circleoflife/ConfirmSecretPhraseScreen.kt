package com.example.circleoflife


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow



@Composable
fun ConfirmSecretPhraseScreen(navController: NavHostController) {
    val correctOrder = listOf("local", "riot", "cost", "fetch", "scheme", "size", "fatal", "erosion", "icon", "boss", "spy", "ten")
    var selectedWords by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Confirm Phrase", color = Color.White, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            correctOrder.shuffled().forEach { word ->
                Button(
                    onClick = { if (selectedWords.size < 12) selectedWords = selectedWords + word },
                    enabled = !selectedWords.contains(word)
                ) {
                    Text(word)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        if (selectedWords == correctOrder) {
            Text("✅ Success", color = Color.Green)
            Button(onClick = { navController.navigate("success") }) {
                Text("Complete Backup")
            }
        } else if (selectedWords.size == 12) {
            Text("❌ Incorrect order", color = Color.Red)
        }
    }
}
