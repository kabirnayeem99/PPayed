package com.kabirnayeem99.paymentpaid.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseUser
import com.kabirnayeem99.paymentpaid.auth.AuthRepository


class LogInRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: AuthRepository = AuthRepository(application)
    private var userLiveData: MutableLiveData<FirebaseUser> = repository.getUserLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> = repository.getLoggedOutLiveData()

    fun login(email: String, password: String) = repository.login(email, password)
    fun logout() = repository.logOut()

    fun register(email: String, password: String) = repository.register(email, password)

    fun getUserLiveData(): MutableLiveData<FirebaseUser> = userLiveData
    fun getLoggedOutLiveData(): MutableLiveData<Boolean> = loggedOutLiveData

}