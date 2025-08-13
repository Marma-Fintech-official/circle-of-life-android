package com.example.circleoflife

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.circleoflife.auth.createGoogleSignInClient
import com.example.circleoflife.auth.sendTokenToBackend
import com.example.circleoflife.ui.theme.CircleOfLifeTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

enum class AuthState { LOGIN, SIGNUP }

@Composable
fun GoogleSignInButton() {
    val context = LocalContext.current
    val activity = context as Activity
    val googleSignInClient = remember { createGoogleSignInClient(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                Log.d("GoogleSignIn", "Token: $idToken")

                // Send this token to your backend
                sendTokenToBackend(idToken)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "SignIn failed: ${e.statusCode}")
            }
        }
    }

    AuthOptionButton(
        iconResId = R.drawable.google,
        text = "Sign in with Google",
        onClick = { launcher.launch(googleSignInClient.signInIntent) }
    )
}


@Composable
fun AuthScreen() {
    var authState by remember { mutableStateOf(AuthState.SIGNUP) }
    var showWalletSheet by remember { mutableStateOf(false) }
    var showInviteSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ComposeColor(0xFFC8DEFF),
                        ComposeColor(0xFFC8FFF2),
                        ComposeColor(0xFFFFC8E2),
                        ComposeColor(0xFFFFF0C8),
                        ComposeColor(0xFFFFFFFF)
                    ),
                    center = Offset.Infinite,
                    radius = 1800f
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(x = canvasWidth / 2, y = canvasHeight * 0.2f)
            for (i in 1..10) {
                drawCircle(
                    color = ComposeColor.White.copy(alpha = 0.25f),
                    radius = (100 * i).dp.toPx(),
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(ComposeColor.White, shape = RoundedCornerShape(32.dp))
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Build a smarter lifestyle,",
                    color = ComposeColor.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "powered by your data.",
                    color = ComposeColor.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            AuthCard(
                authState = authState,
                onAuthStateChange = { authState = it },
                onWalletClick = { showWalletSheet = true },
                onInviteClick = { showInviteSheet = true }
            )
        }

        // === POPUPS (BOTTOM SHEETS) ===
        if (showWalletSheet) {
            WalletTypeSheet(
                onDismiss = { showWalletSheet = false },
                onContinue = { showWalletSheet = false }
            )
        }
        if (showInviteSheet) {
            InviteCodeSheet(
                onContinue = { /* handle code here if needed */ showInviteSheet = false },
                onDismiss = { showInviteSheet = false }
            )
        }
    }
}

@Composable
fun AuthCard(
    authState: AuthState,
    onAuthStateChange: (AuthState) -> Unit,
    onWalletClick: () -> Unit,
    onInviteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ComposeColor.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            TabSelector(
                selectedTab = authState,
                onTabSelected = onAuthStateChange
            )
            Spacer(modifier = Modifier.height(24.dp))
            Crossfade(targetState = authState, label = "auth_content_crossfade") { state ->
                when (state) {
                    AuthState.LOGIN -> LoginContent(
                        onWalletClick = onWalletClick
                    )
                    AuthState.SIGNUP -> SignupContent(
                        onWalletClick = onWalletClick,
                        onInviteClick = onInviteClick
                    )
                }
            }
        }
    }
}

@Composable
fun TabSelector(selectedTab: AuthState, onTabSelected: (AuthState) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ComposeColor(0xFFF3F4F6)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            TabButton(
                text = "Login",
                isSelected = selectedTab == AuthState.LOGIN,
                onClick = { onTabSelected(AuthState.LOGIN) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = "Signup",
                isSelected = selectedTab == AuthState.SIGNUP,
                onClick = { onTabSelected(AuthState.SIGNUP) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = if (isSelected) ComposeColor.White else ComposeColor.Transparent
    val contentColor = if (isSelected) ComposeColor.Black else ComposeColor.Gray

    Button(
        onClick = onClick,
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else null,
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun SignupContent(
    onWalletClick: () -> Unit,
    onInviteClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Get Started now!",
            color = ComposeColor.Black,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthOptionButton(
            iconResId = R.drawable.wallet,
            text = "Signup with wallet",
            onClick = onWalletClick
        )
        Spacer(modifier = Modifier.height(12.dp))
//        AuthOptionButton(
//            iconResId = R.drawable.google,
//            text = "Signup with Google",
//            onClick = { /* implement Google signup here if desired */ }
//        )
        GoogleSignInButton()
        Spacer(modifier = Modifier.height(12.dp))
        AuthOptionButton(
            iconResId = R.drawable.invite_code,
            text = "Got an invite code?",
            onClick = onInviteClick
        )
    }
}

@Composable
fun LoginContent(
    onWalletClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            color = ComposeColor.Black,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthOptionButton(
            iconResId = R.drawable.wallet,
            text = "Signin with wallet",
            onClick = onWalletClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthOptionButton(
            iconResId = R.drawable.google,
            text = "Signin with Google",
            onClick = { /* implement Google signin here if desired */ }
        )
    }
}

@Composable
fun AuthOptionButton(iconResId: Int, text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                tint = ComposeColor.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, color = ComposeColor.Black, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        }
    }
}

// Use your app theme if you want previews.
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AuthScreenPreview() {
    CircleOfLifeTheme {
        AuthScreen()
    }
}

/**
 * ==================
 * Place your WalletTypeSheet & InviteCodeSheet composables in separate files.
 * They should accept onDismiss and onContinue lambdas as in this template.
 * ==================
 */
