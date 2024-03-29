package com.kabirnayeem99.paymentpaid.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var timer: Timer

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        /*
        shows splash screen for 900 milliseconds
        when it checks for the auth state, if it is authenticated already,
        it loads the home activity
        else it loads the sign in activity.
         */
        timer.schedule(object : TimerTask() {
            override fun run() {

                auth.addAuthStateListener { authListener ->
                    if (authListener.currentUser != null) {

                        Timber.d("current user is ${auth.currentUser ?: "null"}")
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    } else {
                        startActivity(Intent(applicationContext, SignInActivity::class.java))
                    }
                }
            }
        }, 900)
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel().also {
            this.finish()
        }
    }
}