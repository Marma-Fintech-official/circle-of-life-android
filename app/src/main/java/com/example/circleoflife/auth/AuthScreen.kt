package com.example.circleoflife.auth

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


fun createGoogleSignInClient(context: android.content.Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("071376456822-dahji2u1vgeiiulaqr8lmjffijfq04mk.apps.googleusercontent.com") // From Google Cloud console
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, gso)
}
