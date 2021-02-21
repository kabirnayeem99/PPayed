package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModel
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_sign_in.*

const val TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var logInRegisterViewModel: LogInRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setUpViewModel()


    }

    private fun setUpViewModel() {
        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)

        logInRegisterViewModel.getUserLiveData().observe(this, Observer { firebaseUser ->
            firebaseUser?.let {
                moveToMainActivity()
            }
        })
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        showLogInRegisterForm()
        setUpRegistrationListener()
        setUpLoginListener()
        setUpNoLoginListener()
        return super.onCreateView(name, context, attrs)
    }

    private fun setUpNoLoginListener() {
        moveToMainActivity()
    }

    private fun setUpLoginListener() {

        btnLogIn.setOnClickListener {
            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()
            try {
                logInRegisterViewModel.login(email, password)
            } catch (e: Exception) {
                Toast.makeText(this, "Can't Login. ${e.message}",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRegistrationListener() {
        btnRegister.setOnClickListener {
            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()
            try {
                logInRegisterViewModel.register(email, password)
            } catch (e: Exception) {
                Toast.makeText(this, "Can't Register. ${e.message}",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLogInRegisterForm() {
        appLogoSplash.visibility = View.GONE
        logInRegisterPart.visibility = View.VISIBLE
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
}