package com.kabirnayeem99.paymentpaid.domain.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.kabirnayeem99.paymentpaid.other.Constants
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
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

        fun FirebaseUser.toUser(): User? {
            return try {
                val id = this.uid
                val name = this.displayName ?: "Unknown"
                val email = this.email ?: "unknown"
                val phoneNumber = this.phoneNumber ?: "unknown"
                val imageUrl = this.photoUrl.toString()
                    ?: "https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI"
                User(id, name, phoneNumber, email, imageUrl)
            } catch (e: Exception) {
                Timber.e(e.message ?: Constants.UNKNOWN_ERROR)
                null
            }
        }

        fun DocumentSnapshot.toUser(): User? {
            try {
                val name = getString("name")!!
                val email = getString("email")!!
                val phoneNumber = getString("phone_number")!!
                val imageUrl = getString("image_url")!!
                return User(id, name, phoneNumber, email, imageUrl)
            } catch (e: Exception) {
                Timber.e("toUser: error converting it to user $e")

                return null
            }
        }
    }
}
