package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kabirnayeem99.paymentpaid.R
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, SignInActivity::class.java))
            }
        }, 900)
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}