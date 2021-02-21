package com.kabirnayeem99.paymentpaid.auth

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository(private val application: Application) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var isOffline: Boolean = true

    init {
        if (firebaseAuth.currentUser != null) {
            isOffline = false
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }


    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    fun getOfflineStatus(): Boolean {
        return isOffline
    }

    fun noLogin() {
        isOffline = true
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        application.mainExecutor,
                        { task ->
                            if (task.isSuccessful) {
                                userLiveData.postValue(firebaseAuth.currentUser)
                            } else {
                                Toast.makeText(application.applicationContext, "Registration Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String?, password: String?) {
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(
                        application.mainExecutor,
                        { task ->
                            if (task.isSuccessful) {
                                userLiveData.postValue(firebaseAuth.currentUser)
                            } else {
                                Toast.makeText(application.applicationContext,
                                        "Login Failure: " +
                                                task.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                )
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }
}
