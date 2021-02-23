package com.kabirnayeem99.paymentpaid.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.kabirnayeem99.paymentpaid.auth.AuthRepository


class LogInRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: AuthRepository = AuthRepository(application)
    private var userLiveData: MutableLiveData<FirebaseUser> = repository.getUserLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> = repository.getLoggedOutLiveData()
    private var isOffline: Boolean = repository.getOfflineStatus()

    fun login(email: String, password: String) = repository.login(email, password)
    fun logout() = repository.logOut()

    fun register(email: String, password: String) = repository.register(email, password)
    fun noLogin() = repository.noLogin()

    fun getUserLiveData(): MutableLiveData<FirebaseUser> = userLiveData
    fun getLoggedOutLiveData(): MutableLiveData<Boolean> = loggedOutLiveData
    fun getOfflineStatus(): Boolean = isOffline

}