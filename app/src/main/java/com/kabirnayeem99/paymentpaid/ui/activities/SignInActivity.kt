package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.auth.AuthRepository
import com.kabirnayeem99.paymentpaid.auth.AuthService
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var repo: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        handleAuthentication()
        repo = AuthRepository()

        auth.signOut()

        if (auth.currentUser != null) {
            moveToMainActivity()
        }

    }

    private fun moveToMainActivity() {
        if (auth.currentUser != null) {
            Toast.makeText(this,
                    "You are signed in with ${auth.currentUser!!.email}",
                    Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,
                    "You signed in without any online syncing support.",
                    Toast.LENGTH_LONG).show()
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun handleAuthentication() {
        auth = FirebaseAuth.getInstance()
        handleLogin()
        handleRegistration()
        handleWithoutLogin()
    }

    private fun handleWithoutLogin() {
        btnWithoutLogin.setOnClickListener {
            pbSigningIn.visibility = View.VISIBLE
            moveToMainActivity()
        }
    }

    private fun handleRegistration() {
        btnRegister.setOnClickListener {

            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        pbSigningIn.visibility = View.VISIBLE
                    }
                    repo.registerUser(email, password, auth)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignInActivity, "${e.message}",
                                Toast.LENGTH_SHORT).show()
                    }
                    withContext(Dispatchers.Main) {
                        pbSigningIn.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun handleLogin() {
        btnLogIn.setOnClickListener {
            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()
            try {

                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        pbSigningIn.visibility = View.VISIBLE
                    }
                    repo.loginUser(email, password, auth)
                    withContext(Dispatchers.Main) {
                        moveToMainActivity()
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "onCreate: ${e.message}")
                Toast.makeText(this@SignInActivity,
                        "${e.message}", Toast.LENGTH_SHORT).show()
                pbSigningIn.visibility = View.GONE
            }
        }
    }
}