package com.kabirnayeem99.paymentpaid.data.db.entities

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize
import java.lang.Exception

@Parcelize
data class User(
        val userId: String,
        val name: String,
        val phoneNumber: String,
        val email: String,
        val imageUrl: String
) : Parcelable {
    companion object {

        private const val TAG = "User"

        fun DocumentSnapshot.toUser(): User? {
            try {
                val name = getString("name")!!
                val email = getString("email")!!
                val phoneNumber = getString("phone_number")!!
                val imageUrl = getString("image_url")!!
                return User(id, name, phoneNumber, email, imageUrl)
            } catch (e: Exception) {
                Log.e(TAG, "toUser: error converting it to user $e")

                return null
            }
        }
    }
}
