package com.example.circleoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun TermsScreen(navController: NavController) {
    var agreed by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        color = Color(0xFF121212)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Terms & Conditions",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Please agree to the terms before continuing. We use AI to enhance your experience. Your data may be used for training purposes.",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = agreed,
                        onValueChange = { agreed = it }
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = agreed, onCheckedChange = { agreed = it })
                Spacer(modifier = Modifier.width(8.dp))
                Text("I agree to the terms and conditions", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate("walletSetup")
                    },
                    enabled = agreed
                ) {
                    Text("I Agree")
                }

                OutlinedButton(
                    onClick = { /* Maybe exit or go back */ },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("No Thanks")
                }
            }
        }
    }
}

