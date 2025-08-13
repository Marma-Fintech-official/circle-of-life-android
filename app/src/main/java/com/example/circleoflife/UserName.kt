package com.example.circleoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.circleoflife.ui.theme.CircleOfLifeTheme



@Composable
fun UserName(navController: NavController) {
    // Soft pastel gradient background based on screenshot
    val pastelGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFE8EAF6), Color(0xFFFBE8F0), Color(0xFFD9F6FB))
    )

    var username by remember { mutableStateOf("") }
    val usernameMaxLength = 15
    val isValidUsername = username.isNotBlank() && username.length <= usernameMaxLength

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            // AppBar
            Spacer(Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Setup Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))

            // ======= REPLACED LINEAR PROGRESS INDICATOR WITH SEGMENTED INDICATOR =======
            val totalSteps = 5
            val currentStep = 0 // Zero-based index: 2 = third step active

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(totalSteps) { index ->
                    ProgressSegment(
                        isActive = index <= currentStep,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            // ==============================================================================

            Spacer(Modifier.height(26.dp))
            // Title
            Text(
                "Your Username",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            // Subtitle
            Text(
                "What would you like to be called?",
                fontSize = 16.sp,
                color = Color(0xFF545454)
            )
            Spacer(Modifier.height(24.dp))
            // Username input with checkmark
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White)
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = username,
                        onValueChange = {
                            if (it.length <= usernameMaxLength) username = it
                        },
                        placeholder = { Text("Your username") },
                        singleLine = true,
                        colors  = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    if (isValidUsername) {
                        Icon(

                            painter = painterResource(id = R.drawable.verified),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 12.dp)
                        )
                    }
                }
            }
            // Character count and info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "*Enhance name should be 15 characters or less",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    "${username.length}/$usernameMaxLength",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
//            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(458.dp))
            // Continue Button
            Button(
                onClick = {navController.navigate("")  },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                enabled = isValidUsername,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Progress segment composable for each step
@Composable
fun ProgressSegment(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color = if (isActive) Color(0xFF67ABFF) else Color(0xFFD6D6D6))
    )
}
