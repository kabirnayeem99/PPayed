package com.kabirnayeem99.paymentpaid.data.repositories

import com.kabirnayeem99.paymentpaid.domain.models.User
import com.kabirnayeem99.paymentpaid.domain.repositories.AuthRepository
import com.kabirnayeem99.paymentpaid.domain.sources.AuthDataSource
import com.kabirnayeem99.paymentpaid.other.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(var dataSource: AuthDataSource) : AuthRepository {
    override fun signInWithEmailAndPassword(email: String, password: String): Resource<User> =
        dataSource.signInWithEmailAndPassword(email, password)

    override fun signWithoutWithEmailAndPassword(): Resource<String> =
        dataSource.signWithoutWithEmailAndPassword()

    override fun registerWithEmailAndPassword(email: String, password: String): Resource<User> =
        dataSource.registerWithEmailAndPassword(email, password)
}