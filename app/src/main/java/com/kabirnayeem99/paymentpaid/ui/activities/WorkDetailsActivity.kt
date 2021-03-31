package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.ui.*
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.android.synthetic.main.activity_add_new_work.*


/**
 * This Activity is for creating a new work or edit an existing work.
 * This [WorkDetailsActivity] extends [AppCompatActivity]
 */
class WorkDetailsActivity : AppCompatActivity() {
    private lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var work: Work

    private var toBeUpdatedWork: Work? = null
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_work)
        setUpUpdateWorkEditing()
        setUpViewModel()
    }


    /**
     * This method checks if any work has been passed down,
     * if not, it proceeds to fill the text field by calling
     * the [fillTheFieldIfExistingWork] function
     */
    private fun setUpUpdateWorkEditing() {
        toBeUpdatedWork = getWorkFromIntent()
        toBeUpdatedWork?.documentId?.let { fillTheFieldIfExistingWork(toBeUpdatedWork!!) }
    }

    /**
     * It gets the work that is passed down from the work list
     */
    private fun getWorkFromIntent(): Work? {
        val intent: Intent = intent

        val bundle = intent.extras
        return bundle?.getSerializable("work") as? Work

    }

    /**
     * This function fills the text fields if this work is a work
     * to be updated
     * @param workBeingProcessed which is a work object
     */
    private fun fillTheFieldIfExistingWork(workBeingProcessed: Work) {
        tilWorkName.editText?.setText(workBeingProcessed.name)
        tilStudentName.editText?.setText(workBeingProcessed.studentName)
        tilPayment.editText?.setText(workBeingProcessed.payment.toString())
        dpDate.updateDate(workBeingProcessed.year.toInt(), workBeingProcessed.month.toInt(), workBeingProcessed.day.toInt())
    }

    /**
     * overrides on back press function
     * with the back function, it checks if the work is a new work
     * or a work that already is exists
     * if new work it proceeds to insert it into the database
     * if existing work it proceeds to update the work in the database
     */
    override fun onBackPressed() {

        createOrUpdateWork()?.let { work ->
            if (toBeUpdatedWork?.documentId != null) {
                val success = firestoreViewModel.saveWork(work)
            } else {
                firestoreViewModel.saveWork(work)
            }
            Toast.makeText(this, "Your work was saved.", Toast.LENGTH_SHORT).show()
        }
        super.onBackPressed()
//        (this as Activity).overridePendingTransition(R.anim.slide_out_right, R.anim.fade_exit)
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    /**
     * This methods sets up the view model for this activity
     */
    private fun setUpViewModel() {
        lateinit var logInRegisterViewModel: LogInRegisterViewModel
        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)

        when (logInRegisterViewModel.getOfflineStatus()) {
            true -> {

                firestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel::class.java)
            }
            false -> {
                firestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel::class.java)
            }
        }

    }

    /**
     * It creates a new work or updates a work, based on the work type passed down
     * from the previous activity
     * @return Work? which can be null, if it fails to create work object
     */
    private fun createOrUpdateWork(): Work? {
        val workName: String = tilWorkName.editText?.text.toString()
        val studentName: String = tilStudentName.editText?.text.toString()
        val paymentAmount: String = tilPayment.editText?.text.toString()

        var month = CustomUtils.currentMonth
        var year = CustomUtils.currentYear
        var day = CustomUtils.currentDay

        dpDate?.let { dpDate ->
            month = dpDate.month + 1
            Log.d(TAG, "createOrUpdateWork: month $month")
            year = dpDate.year
            day = dpDate.dayOfMonth
        }

        /*
        returns null if any of the field is empty
        or returns a newly created work object
         */
        return if (workName.trim().isEmpty()
                || studentName.trim().isEmpty()
                || paymentAmount.trim().isEmpty()) {
            null
        } else {
            work = Work(documentId = auth.currentUser.uid + "3495", name = workName, day = day.toLong(), month = month.toLong(),
                    year = year.toLong(), payment = paymentAmount.toLong(), studentName = studentName.toString())



            toBeUpdatedWork?.let { workToBeUpdated ->
                work.documentId = workToBeUpdated.documentId
            }

            work
        }

    }

}


