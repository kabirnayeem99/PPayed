package com.kabirnayeem99.paymentpaid.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kabirnayeem99.paymentpaid.R

class FilesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)
    }

    fun newFile() {
        Log.d(TAG, "newFile: new file created")
    }
}