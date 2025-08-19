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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InviteCodeSheet(
    onContinue: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var code by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .clickable { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(horizontal = 18.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Drag handle
            Box(
                Modifier
                    .width(40.dp)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEAEAEA))
            )
            Spacer(Modifier.height(20.dp))

            // Title
            Text(
                "Got an invite code?",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(22.dp))

            // Input row: Separate TextField and Paste Icon containers
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color(0xFFF6F8FA))
                        .border(1.dp, Color(0xFFE3E6EC), RoundedCornerShape(13.dp))
                ) {
                    TextField(
                        value = code,
                        onValueChange = { code = it },
                        placeholder = {
                            Text(
                                "Eg. ABI1670-3456",
                                fontSize = 16.sp,
                                color = Color(0xFFAEB8C9)
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp),
                        textStyle = TextStyle(fontSize = 16.sp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color(0xFF3B82F6))
                        .clickable {
                            val clipboardText = clipboardManager.getText()
                            if (clipboardText != null) {
                                code = clipboardText.text
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_clipboard),
                        contentDescription = "Paste",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))

            // Continue Button
            Button(
                onClick = { onContinue(code) },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Continue", fontWeight = FontWeight.Medium, fontSize = 18.sp)
            }
        }
    }
}
