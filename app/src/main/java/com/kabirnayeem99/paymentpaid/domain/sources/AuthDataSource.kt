package com.kabirnayeem99.paymentpaid.domain.sources

import com.kabirnayeem99.paymentpaid.domain.models.User
import com.kabirnayeem99.paymentpaid.other.Resource

interface AuthDataSource {
    fun signInWithEmailAndPassword(email: String, password: String): Resource<User>
    fun signWithoutWithEmailAndPassword(): Resource<String>
    fun registerWithEmailAndPassword(email: String, password: String): Resource<User>
}