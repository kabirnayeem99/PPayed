package com.kabirnayeem99.paymentpaid.presentation

import androidx.lifecycle.ViewModel
import com.kabirnayeem99.paymentpaid.domain.models.User
import com.kabirnayeem99.paymentpaid.domain.repositories.AuthRepository
import com.kabirnayeem99.paymentpaid.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(var repo: AuthRepository) : ViewModel() {

    var email: String = ""
    var password: String = ""


    fun signInWithEmailAndPassword(): Resource<User> {
        if (email.isEmpty()) {
            return Resource.Error("Empty email")
        }

        if (password.length < 6) {
            return Resource.Error("Password is too short.")
        }

        return repo.signInWithEmailAndPassword(email, password)
    }

    fun signWithoutWithEmailAndPassword() = repo.signWithoutWithEmailAndPassword()

    fun registerWithEmailAndPassword(): Resource<User> {
        if (email.isEmpty()) {
            return Resource.Error("Empty email")
        }

        return repo.registerWithEmailAndPassword(email, password)
    }

}