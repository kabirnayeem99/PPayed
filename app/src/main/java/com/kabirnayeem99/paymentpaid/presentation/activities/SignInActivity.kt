package com.kabirnayeem99.paymentpaid.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.other.Resource
import com.kabirnayeem99.paymentpaid.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setUpAuthListener()
        showLogInRegisterForm()
        setUpRegistrationListener()
        setUpLoginListener()
        setUpNoLoginListener()
    }

    override fun onStart() {
        super.onStart()
        etEmailAddress.setText(authViewModel.email)
        etPassword.setText(authViewModel.password)

        etEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                authViewModel.email = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                authViewModel.password = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onPause() {
        super.onPause()
        authViewModel.email = etEmailAddress.text.toString()
        authViewModel.password = etPassword.text.toString()
    }

    private fun setUpAuthListener() {
        auth.addAuthStateListener { authState ->
            if (authState.currentUser != null) {
                moveToMainActivity()
            }
        }
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
    }


    private fun setUpNoLoginListener() {
        btnWithoutLogin.setOnClickListener {
            showLoading()
            when (val signInResource = authViewModel.signWithoutWithEmailAndPassword()) {
                is Resource.Error -> {
                    hideLoading()
                    getSnackBar(signInResource.message)
                }
                else -> {
                    getSnackBar("Logged in temporarily.").show()
                    moveToMainActivity()
                }
            }
        }
    }

    private fun setUpLoginListener() {

        btnLogIn.setOnClickListener { btn ->

            showLoading()


            when (val signInResource = authViewModel.signInWithEmailAndPassword()) {
                is Resource.Error -> {
                    hideLoading()
                    getSnackBar(signInResource.message).show()
                }
                else -> {
                    getSnackBar("Logged in with ${signInResource.data?.email ?: "unknown email address"}").show()
                    moveToMainActivity()
                }
            }
        }
    }

    private fun getSnackBar(message: String?): Snackbar {
        return Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            message ?: "Something went wrong",
            Snackbar.LENGTH_LONG
        )
    }

    private fun showLoading() {
        pbSigningIn.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbSigningIn.visibility = View.GONE

    }

    /**
     * This method starts the registration listener
     * Where if the registration button is clicked
     */
    private fun setUpRegistrationListener() {
        btnRegister.setOnClickListener {
            showLoading()



            when (val registerResource =
                authViewModel.registerWithEmailAndPassword()) {
                is Resource.Error -> {
                    hideLoading()
                    getSnackBar(registerResource.message).show()
                }
                else -> {
                    getSnackBar("Registered with ${registerResource.data?.email ?: "unknown email"}").show()
                    moveToMainActivity()
                }
            }

        }
    }


    /**
     * This method shows the login and registration form, which is hidden in start
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