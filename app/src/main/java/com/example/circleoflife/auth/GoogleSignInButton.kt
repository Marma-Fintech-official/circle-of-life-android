package com.example.circleoflife.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.circleoflife.AuthOptionButton
import com.example.circleoflife.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

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
