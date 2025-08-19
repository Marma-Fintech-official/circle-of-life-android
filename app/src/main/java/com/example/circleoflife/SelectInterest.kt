package com.example.circleoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.compose.foundation.layout.FlowRow


@Composable
fun TopicSelectScreen(navController: NavController) {
    val pastelGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF8FAFF),
            Color(0xFFFDEBED),
            Color(0xFFE8EDF8)
        )
    )
    val topics = listOf(
        "🎷 Music", "👗 Fashion", "🎮 Games", "📖 Reading",
        "🐕 Pets", "✈️ Travel", "💻 Science & Technology",
        "💄 Beauty", "🍲 Food", "🧑‍🦰 Environment",
        "🧴 Self-Care", "🍥 Wellness", "💼 Finance",
        "🕶 Purpose & Motivation", "🧱 Architecture", "📝 Art",
        "🏀 Sport", "🎬 Movies", "🎭 Drama", "🏥 Health",
        "🥗 Nutrition", "👨‍👩‍👧 Parenthood", "💑 Relationships"
    )
    // State for selection
    var selectedTopics by remember { mutableStateOf(setOf<Int>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = pastelGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(18.dp))
            // App bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f) // bring above
            ) {
                IconButton(onClick = { /* Handle back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "Setup Account",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(8.dp))
            // Progress indicator (simulate 2/4)
            LinearProgressIndicator(
                progress = 0.5f,
                color = Color(0xFF8456E4),
                trackColor = Color(0xFFD6D6D6),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            Spacer(Modifier.height(28.dp))
            // Title, subtitle
            Text(
                "Select Topic That\nInterest You",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Choose from a diverse range of interests that align with your passions and preferences.",
                lineHeight = 22.sp,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Spacer(Modifier.height(24.dp))
            // Chips grid
            @OptIn(ExperimentalLayoutApi::class)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f)
            ) {
                topics.forEachIndexed { idx, label ->
                    val selected = selectedTopics.contains(idx)
                    TopicChip(
                        label = label,
                        selected = selected,
                        onClick = {
                            selectedTopics = if (selected)
                                selectedTopics - idx else selectedTopics + idx
                        }
                    )
                }
            }
            // Continue Button
            Button(
                onClick = { navController.navigate("UploadPhotoScreen") },
                enabled = selectedTopics.isNotEmpty(),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTopics.isNotEmpty()) Color.Black else Color.LightGray,
                    contentColor = Color.White
                )
            ) {
                Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TopicChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = if (selected) Color(0xFF8456E4) else Color(0xFFF8F7FB),
        border = if (selected) null else ButtonDefaults.outlinedButtonBorder,
        modifier = Modifier
            .clickable { onClick() }
            .defaultMinSize(minHeight = 44.dp)
    ) {
        Text(
            label,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (selected) Color.White else Color.Black,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}



