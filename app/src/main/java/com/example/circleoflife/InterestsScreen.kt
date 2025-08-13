package com.example.circleoflife


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.circleoflife.ui.theme.ProgressIndicator // Assuming ProgressIndicator is in this package

// Data class to hold interest information
data class Interest(val name: String, val emoji: String)

// The list of all available interests
val allInterests = listOf(
    Interest("Health & Wellness", "🥗"),
    Interest("Personal Growth", "🏅"),
    Interest("Fashion & Beauty", "👗"),
    Interest("Shopping", "🛍️"),
    Interest("Business & Finance", "💼"),
    Interest("Current Affairs", "📰"),
    Interest("Learning & Knowledge", "📚"),
    Interest("Environment & Nature", "🌍"),
    Interest("Travel & Adventure", "✈️"),
    Interest("Food", "🍔"),
    Interest("Entertainment & Leisure", "🎬"),
    Interest("Sports", "🏆"),
    Interest("Creative Hobbies", "🎨"),
    Interest("Collecting Hobbies", "🖼️"),
    Interest("Pet Lovers", "🐾"),
    Interest("Belief & Spirituality", "🙏")
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InterestsScreen(navController: NavController) {
    // State to keep track of the selected interests
    var selectedInterests by remember { mutableStateOf(setOf<Interest>()) }
    val isContinueEnabled = selectedInterests.size >= 3

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Account", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEADDFF),
                            Color(0xFFFEF4D5),
                            Color.White
                        ),
                        startY = 0f,
                        endY = 3000f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
            ) {
                // Progress Bar - This is the 4th and final step
                ProgressIndicator(currentStep = 4, totalSteps = 4)
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Pick your interests",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose at least three.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))

                // --- Interest Chips ---
                // FlowRow automatically wraps items to the next line
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    allInterests.forEach { interest ->
                        InterestChip(
                            interest = interest,
                            isSelected = interest in selectedInterests,
                            onSelect = {
                                selectedInterests = if (it) {
                                    selectedInterests + interest
                                } else {
                                    selectedInterests - interest
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // --- Continue Button ---
                Button(
                    onClick = {
                        // Navigate to the main app dashboard or home screen
                        // navController.navigate("home") { popUpTo("setup_graph") { inclusive = true } }
                    },
                    enabled = isContinueEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    )
                ) {
                    Text(text = "Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InterestChip(
    interest: Interest,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    val activeColor = Color(0xFF6A5AE0)

    OutlinedButton(
        onClick = { onSelect(!isSelected) },
        shape = RoundedCornerShape(50), // Fully rounded
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) activeColor else Color.LightGray
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) activeColor.copy(alpha = 0.1f) else Color.Transparent,
            contentColor = if (isSelected) activeColor else Color.DarkGray
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = interest.emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = interest.name, fontWeight = FontWeight.SemiBold)
        }
    }
}