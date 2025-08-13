package com.example.circleoflife.auth

import android.util.Log
import okhttp3.*
import java.io.IOException

fun sendTokenToBackend(idToken: String?) {
    if (idToken == null) return

    val client = OkHttpClient()
    val requestBody = FormBody.Builder()
        .add("token", idToken)
        .build()

    val request = Request.Builder()
        .url("http://10.0.2.2:4000/api/google") // Emulator localhost
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Backend", "Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.d("Backend", "Response: ${response.body?.string()}")
        }
    })
}
