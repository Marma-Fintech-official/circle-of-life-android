package com.example.circleoflife

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.circleoflife.ui.theme.ProgressIndicator // Assuming ProgressIndicator is in this package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadSuccessScreen(
    navController: NavController,
    imageUri: Uri?,
    emoji: String?,
    @DrawableRes avatarResId: Int?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Account", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Go back
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Handle Skip */ }) {
                        Text("Skip", color = MaterialTheme.colorScheme.primary)
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
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress Bar - This is the final step
                ProgressIndicator(currentStep = 4, totalSteps = 4)

                // Use a weighted column to push the button to the bottom
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Uploaded Successfully!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Well done! Your effort is shaping your story.\nEvery upload adds meaning to your Circle.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(48.dp))

                    // --- The selected profile picture ---
                    Box(contentAlignment = Alignment.BottomEnd) {
                        // Display the correct profile picture based on what was passed
                        when {
                            imageUri != null -> {
                                AsyncImage(model = imageUri, contentDescription = "Profile Photo", modifier = Modifier.size(150.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                            }
                            emoji != null -> {
                                Box(modifier = Modifier.size(150.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape), contentAlignment = Alignment.Center) {
                                    Text(text = emoji, fontSize = 80.sp)
                                }
                            }
                            avatarResId != null -> {
                                Image(painter = painterResource(id = avatarResId), contentDescription = "Avatar", modifier = Modifier.size(150.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                            }
                            else -> {
                                // Fallback placeholder
                                Icon(Icons.Default.Person, "Placeholder", tint = Color.Gray, modifier = Modifier.size(150.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape).padding(24.dp))
                            }
                        }

                        // Checkmark Icon
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFF6A5AE0), // Active color
                            modifier = Modifier
                                .size(40.dp)
                                .offset(x = 5.dp, y = 5.dp) // Adjust position slightly
                                .background(Color.White, CircleShape)
                        )
                    }
                }

                // --- Continue Button ---
                Button(
                    onClick = {
                        navController.navigate("interests")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = "Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}