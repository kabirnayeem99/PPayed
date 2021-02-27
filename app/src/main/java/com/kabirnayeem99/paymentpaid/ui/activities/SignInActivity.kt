package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModel
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_sign_in.*

const val TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {

    private lateinit var logInRegisterViewModel: LogInRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setUpViewModel()
        showLogInRegisterForm()
        setUpRegistrationListener()
        setUpLoginListener()
        setUpNoLoginListener()
    }

    /**
     * This method sets up [LogInRegisterViewModel] for the [SignInActivity]
     */
    private fun setUpViewModel() {
        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)

        logInRegisterViewModel.getUserLiveData().observe(this, { firebaseUser ->
            if (firebaseUser != null) {
                pbSigningIn.visibility = View.GONE
                Toast.makeText(this,
                        "You are signed in with ${firebaseUser.email}",
                        Toast.LENGTH_LONG).show()
                moveToMainActivity()
            }
        })

    }


    private fun setUpNoLoginListener() {
        btnWithoutLogin.setOnClickListener {
            logInRegisterViewModel.noLogin().also {
                moveToMainActivity().also {
                    Toast.makeText(this,
                            "You signed in without any online syncing support.",
                            Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    private fun setUpLoginListener() {

        btnLogIn.setOnClickListener {
            pbSigningIn.visibility = View.VISIBLE

            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()
            try {
                logInRegisterViewModel.login(email, password)
            } catch (e: Exception) {
                pbSigningIn.visibility = View.GONE.also {
                    Toast.makeText(this, "Can't Login. ${e.message}",
                            Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    /**
     * This method starts the registration listener
     * Where if the registration button is clicked
     */
    private fun setUpRegistrationListener() {
        btnRegister.setOnClickListener {
            pbSigningIn.visibility = View.VISIBLE
            val email: String = etEmailAddress.text.toString()
            val password: String = etPassword.text.toString()
            try {
                logInRegisterViewModel.register(email, password)
            } catch (e: Exception) {
                Toast.makeText(this, "Can't Register. ${e.message}",
                        Toast.LENGTH_SHORT).show()
                pbSigningIn.visibility = View.GONE
            }
        }
    }

    /**
     * This method shows the login and registration form, which is hidden now
     */
    private fun showLogInRegisterForm() {
        appLogoSplash.visibility = View.GONE
        logInRegisterPart.visibility = View.VISIBLE
        pbSigningIn.visibility = View.GONE
    }


    /**
     * This method starts the [HomeActivity]
     */
    private fun moveToMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}