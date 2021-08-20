package com.kabirnayeem99.paymentpaid.domain.repositories

import com.kabirnayeem99.paymentpaid.domain.models.User
import com.kabirnayeem99.paymentpaid.other.Resource

interface AuthRepository {

    fun signInWithEmailAndPassword(email: String, password: String): Resource<User>
    fun signWithoutWithEmailAndPassword(): Resource<String>
    fun registerWithEmailAndPassword(email: String, password: String): Resource<User>

}