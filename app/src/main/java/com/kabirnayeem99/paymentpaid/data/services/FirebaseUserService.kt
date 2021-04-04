package com.kabirnayeem99.paymentpaid.data.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kabirnayeem99.paymentpaid.data.db.entities.User
import com.kabirnayeem99.paymentpaid.data.db.entities.User.Companion.toUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FirebaseUserService {
    private const val TAG = "FirebaseUserService"

    suspend fun getUserProfile(userId: String): User? {
        val db = FirebaseFirestore.getInstance()

        return try {
            db.collection("users").document(userId).get().await().toUser()
        } catch (e: Exception) {
            Log.e(TAG, "getUserProfile: failed to convert firebase data to user $e")
            null
        }
    }
}