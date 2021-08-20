package com.kabirnayeem99.paymentpaid.data.sources

import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.domain.models.User
import com.kabirnayeem99.paymentpaid.domain.models.User.Companion.toUser
import com.kabirnayeem99.paymentpaid.domain.sources.AuthDataSource
import com.kabirnayeem99.paymentpaid.other.Constants
import com.kabirnayeem99.paymentpaid.other.Resource

class FirebaseAuthDataSource : AuthDataSource {

    val auth = FirebaseAuth.getInstance()

    override fun signInWithEmailAndPassword(email: String, password: String): Resource<User> {

        try {
            auth.signInWithEmailAndPassword(email, password)

            val user = auth.currentUser?.toUser()
            if (user != null) {
                return Resource.Success(user)
            } else {
                return Resource.Error("User could not be generated")
            }
        } catch (e: Exception) {
            return Resource.Error("Could not log in due to ${e.message ?: Constants.UNKNOWN_ERROR}")
        }
    }

    override fun signWithoutWithEmailAndPassword(): Resource<String> {
        try {
            auth.signInAnonymously()
            val uid = auth.currentUser?.uid
            if (uid != null) {
                return Resource.Success(uid)
            } else {
                return Resource.Error("Could not log you in.")
            }
        } catch (e: Exception) {
            return Resource.Error("Could not log in due to ${e.message ?: Constants.UNKNOWN_ERROR}")
        }

    }

    override fun registerWithEmailAndPassword(email: String, password: String): Resource<User> {
        try {
            auth.createUserWithEmailAndPassword(email, password)

            val user = auth.currentUser?.toUser()
            if (user != null) {
                return Resource.Success(user)
            } else {
                return Resource.Error("User could not be generated")
            }
        } catch (e: Exception) {
            return Resource.Error("Could not register due to ${e.message ?: Constants.UNKNOWN_ERROR}")
        }
    }
}