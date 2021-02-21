package com.kabirnayeem99.paymentpaid.auth

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.ui.activities.TAG
import com.kabirnayeem99.paymentpaid.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthService {
    suspend fun registerUser(email: String, password: String, auth: FirebaseAuth) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Log.d(TAG, "onCreate: ${e.message}")
        }

    }

    suspend fun loginUser(email: String, password: String, auth: FirebaseAuth) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Log.d(TAG, "onCreate: ${e.message}")
        }
    }


}