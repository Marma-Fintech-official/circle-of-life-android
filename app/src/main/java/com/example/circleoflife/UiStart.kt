package com.example.circleoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun CircleOfLifeScreen(navController: NavController) {
    // Gradient background colors
    val gradient = Brush.verticalGradient(
        0f to Color(0xFF0A0836),
        1f to Color(0xFF8446E4)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            AnimatedGlowingCircle()
            Spacer(modifier = Modifier.height(20.dp))
            WelcomeTextSection()
            Spacer(modifier = Modifier.height(32.dp))
            LetsGoButton(navController)
            Spacer(modifier = Modifier.weight(1f))
            TermsText()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AnimatedGlowingCircle() {
    // Pulsing animation for glowing effect

        // Outer Glow
    Image(
        painter = painterResource(id = R.drawable.group), // replace with your image name
        contentDescription = "Glowing Circle",
        modifier = Modifier
            .padding(top = 40.dp)
            .size(180.dp)
    )

}

@Composable
fun WelcomeTextSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "WELCOME TO\nCIRCLE OF LIFE",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Lets Build your digital Twin",
            fontSize = 16.sp,
            color = Color(0xFFCCC2F8)
        )
    }
}

@Composable
fun LetsGoButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("UserName") },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF3A2D85)
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text("Lets go!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
fun TermsText() {
    Text(
        "By continuing, you agree to COL’s Terms of Service and Privacy Policy",
        fontSize = 12.sp,
        color = Color.White.copy(alpha = 0.6f),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
