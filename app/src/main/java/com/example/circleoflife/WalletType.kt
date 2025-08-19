package com.example.circleoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WalletTypeSheet(
    onDismiss: () -> Unit,
    onContinue: () -> Unit,
) {
    // The sheet container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() } // dismiss when tapping outside// Simulate blur with semi-black tint



    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
        ) {
            // Drag handle
            Box(
                Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xFFEAEAEA))
            )
            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                "Choose your wallet type",
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(26.dp))

            // Two wallet cards side by side
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterHorizontally)
            ) {
                WalletOptionCard(
                    icon = painterResource(id = R.drawable.evm),
                    title = "EVM Wallet",

//                    selected = true // You can control this with state
                )
                WalletOptionCard(
                    icon = painterResource(id = R.drawable.solana),
                    title = "Solana Wallet",
//                    selected = false
                )
            }
            Spacer(Modifier.height(22.dp))

            // Create Wallet Option
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.dp, Color(0xFFE3E6EC), RoundedCornerShape(13.dp))
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .clickable { /* Handle create wallet */ }
                    .padding(horizontal = 14.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = null,
                    tint = Color(0xFF7A859B),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Create your Wallet",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF363C46)
                )
            }

            Spacer(Modifier.height(24.dp))
            // Continue Button
            Button(
                onClick = onContinue,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun WalletOptionCard(
    icon: Painter,
    title: String,
    selected: Boolean = false
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Color(0xFFF2F5FF) else Color(0xFFDFE6F6))
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) Color(0xFF627EEA) else Color(0xFFE3E6EC),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { /* handle click */ }
            .padding(16.dp), // inner padding, customize as needed
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(34.dp),
            tint = Color.Unspecified
        )
        Spacer(Modifier.height(10.dp))
        Text(
            title,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
