package com.kabirnayeem99.paymentpaid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PpayedApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }
}