package com.example.circleoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    val pastelGradient = Brush.radialGradient(
        colors = listOf(
            Color(0xFFF6F9FC),
            Color(0xFFFDEEEA),
            Color(0xFFE8F1FF),
            Color(0xFFF7ECFF)
        ),
        center = Offset(540f, 420f),
        radius = 1100f
    )

    var selectedTab by remember { mutableStateOf("Signup") }
    var showWalletTypeSheet by remember { mutableStateOf(false) }
    var showInviteCodeSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))
            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(Color.White)
            )
            Spacer(Modifier.height(32.dp))
            Text(
                "Build a smarter lifestyle,\npowered by your data.",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF232323)
            )
            Spacer(Modifier.height(32.dp))
            // Login/Signup toggle container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .border(1.dp, Color(0xFFE3E5EA), RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Login Tab
                    Box(
                        Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedTab == "Login") Color.White else Color(0xFFF6F8FA))
                            .border(
                                width = if (selectedTab == "Login") 2.dp else 0.dp,
                                color = if (selectedTab == "Login") Color(0xFFE3E6F0) else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedTab = "Login" }
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Login",
                            fontWeight = if (selectedTab == "Login") FontWeight.Bold else FontWeight.Normal,
                            color = Color(0xFF232323),
                            fontSize = 17.sp
                        )
                    }
                    // Signup Tab
                    Box(
                        Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedTab == "Signup") Color.White else Color(0xFFF6F8FA))
                            .border(
                                width = if (selectedTab == "Signup") 2.dp else 0.dp,
                                color = if (selectedTab == "Signup") Color(0xFFE3E6F0) else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedTab = "Signup" }
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Signup",
                            fontWeight = if (selectedTab == "Signup") FontWeight.Bold else FontWeight.Normal,
                            color = Color(0xFF232323),
                            fontSize = 17.sp
                        )
                    }
                }
            }
            Spacer(Modifier.height(22.dp))
            Text(
                "Get Started now!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp)
            )
            // Signup options
            SignupOptionButton(
                icon = painterResource(id = R.drawable.wallet),
                text = "Signup with wallet",
                onClick = { showWalletTypeSheet = true }
            )
            Spacer(Modifier.height(12.dp))
            SignupOptionButton(
                onClick = { showWalletTypeSheet = true },
                icon = painterResource(id = R.drawable.google),
                text = "Signup with Google"
            )
            Spacer(Modifier.height(12.dp))
            SignupOptionButton(
                icon = painterResource(id = R.drawable.invite_code),
                text = "Got an invite code?",
                textColor = Color(0xFF1565C0),
                borderColor = Color(0xFFB2DAFF),
                onClick = {
                    showInviteCodeSheet = true
                }
            )
        }
        if (showWalletTypeSheet) {
            WalletTypeSheet(
                onDismiss = { showWalletTypeSheet = false },
                onContinue = {
                    // Your continuation logic here
                    showWalletTypeSheet = false
                }
            )
        }
        if (showInviteCodeSheet) {
            InviteCodeSheet(
                onContinue = { code ->
                    // Handle invite code
                    showInviteCodeSheet = false
                },
//                onPasteClicked = {
//                    // Handle paste to code from clipboard
//                },
                onDismiss = {
                    showInviteCodeSheet = false
                }
            )
        }
    }

    }

@Composable
fun SignupOptionButton(
    icon: Painter,
    text: String,
    textColor: Color = Color(0xFF232323),
    borderColor: Color = Color(0xFFE3E5EA),
    onClick: () -> Unit,

    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
            .background(Color.White, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}
