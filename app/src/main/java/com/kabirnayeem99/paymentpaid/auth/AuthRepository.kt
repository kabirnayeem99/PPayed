package com.kabirnayeem99.paymentpaid.auth

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    var authService = AuthService()

    suspend fun registerUser(email: String, password: String, auth: FirebaseAuth) {
        authService.registerUser(email, password, auth)
    }

    suspend fun loginUser(email: String, password: String, auth: FirebaseAuth) {
        authService.loginUser(email, password, auth)
    }
}
