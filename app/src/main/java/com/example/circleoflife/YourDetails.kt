package com.example.circleoflife
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar
import java.util.Date

// A simple enum to manage gender selection state
enum class Gender {
    MALE, FEMALE, PREFER_NOT_TO_SAY, NONE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupAccountScreen(navController: NavController) {
    // State variables to hold user input
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.NONE) }
    val context = LocalContext.current

    // Date Picker Dialog
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            // Format the date as dd/mm/yyyy
            birthDate = "${String.format("%02d", selectedDay)}/${String.format("%02d", selectedMonth + 1)}/$selectedYear"
        }, year, month, day
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Account", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Handle Skip */ }) {
                        Text("Skip", color = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        // --- THIS ENTIRE BACKGROUND MODIFIER IS UPDATED ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEADDFF), // A soft purple
                            Color(0xFFFEF4D5), // A soft yellow
                            Color.White
                        ),
                        // Start the gradient from the top
                        startY = 0f,
                        // End the gradient far below the screen to make it very soft
                        endY = 3000f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress Bar (Updated to match new screenshot)
                ProgressIndicator(currentStep = 2, totalSteps = 4)
                Spacer(modifier = Modifier.height(32.dp))

                // Your Details Section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Your Details",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Birthdate Picker
                    Text(
                        text = "Pick your birthdate from the calendar",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("dd/mm/yyyy") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() },
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = "Open Calendar",
                                modifier = Modifier.clickable { datePickerDialog.show() }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6A5AE0),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Gender Selection
                    Text(
                        text = "Choose the option that best describes you",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GenderOption(
                            drawableResId = R.drawable.male_avatar,
                            label = "Male",
                            isSelected = selectedGender == Gender.MALE,
                            onClick = { selectedGender = Gender.MALE }
                        )
                        GenderOption(
                            drawableResId = R.drawable.female_avatar,
                            label = "Female",
                            isSelected = selectedGender == Gender.FEMALE,
                            onClick = { selectedGender = Gender.FEMALE }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- THIS IS THE NEW BUTTON ---
                    val isPreferNotToSaySelected = selectedGender == Gender.PREFER_NOT_TO_SAY
                    val activeColor = Color(0xFF6A5AE0)

                    OutlinedButton(
                        onClick = { selectedGender = Gender.PREFER_NOT_TO_SAY },
                        shape = CircleShape,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isPreferNotToSaySelected) activeColor else Color.LightGray
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isPreferNotToSaySelected) activeColor else Color.Transparent,
                            contentColor = if (isPreferNotToSaySelected) Color.White else Color.DarkGray
                        )
                    ) {
                        Text(
                            text = "Prefer not to say",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

                // Continue Button
                Button(
                    onClick = {
                        println("Date: $birthDate, Gender: $selectedGender")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
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
fun ProgressIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 1..totalSteps) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (i <= currentStep) Color(0xFF62A2F3) else Color.LightGray.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
fun GenderOption(
    drawableResId: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF6A5AE0) else Color.Transparent
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = drawableResId),
            contentDescription = label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(width = 3.dp, color = borderColor, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = if (isSelected) Color.Black else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}


