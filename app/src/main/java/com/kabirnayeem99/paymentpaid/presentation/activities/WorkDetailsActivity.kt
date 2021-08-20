package com.kabirnayeem99.paymentpaid.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.presentation.*
import com.kabirnayeem99.paymentpaid.other.Utils
import kotlinx.android.synthetic.main.activity_add_new_work.*
import timber.log.Timber
import java.util.*


/**
 * This Activity is for creating a new work or edit an existing work.
 * This [WorkDetailsActivity] extends [AppCompatActivity]
 */
class WorkDetailsActivity : AppCompatActivity() {
    private lateinit var work: Work

    private var toBeUpdatedWork: Work? = null
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_work)
        setUpUpdateWorkEditing()
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
        return bundle?.getParcelable("work") as? Work

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
        dpDate.updateDate(
            workBeingProcessed.year.toInt(),
            workBeingProcessed.month.toInt(),
            workBeingProcessed.day.toInt()
        )
    }

    /**
     * overrides on back press function
     * with the back function, it checks if the work is a new work
     * or a work that already is exists
     * if new work it proceeds to insert it into the database
     * if existing work it proceeds to update the work in the database
     */
    override fun onBackPressed() {

        /*
        Checks if the work object has been created successfully or not,
        if it is created successfully, it proceeds to save the work.
         */
        createOrUpdateWork()?.let { work ->
            try {
                Timber.d("onBackPressed: the saved work is \n $work")
                workViewModel.saveWork(work)
                Toast.makeText(this, "Your work was saved.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Your work was not saved.", Toast.LENGTH_SHORT).show()
                Timber.e("onBackPressed: work was not saved due to $e")
            } finally {
                super.onBackPressed()
            }

        }
    }

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    /**
     * This methods sets up the view model for this activity
     */

    private val workViewModel: WorkViewModel by viewModels()

    /**
     * It creates a new work or updates a work, based on the work type passed down
     * from the previous activity
     * @return Work? which can be null, if it fails to create work object
     */
    private fun createOrUpdateWork(): Work? {
        val workName: String = tilWorkName.editText?.text.toString()
        val studentName: String = tilStudentName.editText?.text.toString()
        val payment: String = tilPayment.editText?.text.toString()

        var month = Utils.currentMonth
        var year = Utils.currentYear
        var day = Utils.currentDay

        dpDate?.let { dpDate ->
            month = dpDate.month + 1
            Timber.d("createOrUpdateWork: month $month")
            year = dpDate.year
            day = dpDate.dayOfMonth
        }

        /*
        returns null if any of the field is empty
        or returns a newly created work object
         */
        return if (workName.trim().isEmpty()
            || studentName.trim().isEmpty()
            || payment.trim().isEmpty()
        ) {
            null
        } else {
            work = Work(
                documentId = null, name = workName, day = day.toLong(), month = month.toLong(),
                year = year.toLong(), payment = payment.toLong(), studentName = studentName
            )

            /*
             checks if there is any extra work from previous intent
             or in other word, it checks if any work from the list
             or fab icon was clicked, it it is an old work, it sets the document id of the previous work
             or if it is a new work

             it creates new document id by combining user id and time in milli second
             */
            if (toBeUpdatedWork == null) {
                work.documentId =
                    Calendar.getInstance().timeInMillis.toString() + auth.currentUser?.uid
            } else if (toBeUpdatedWork != null) {
                toBeUpdatedWork?.let { workToBeUpdated ->
                    work.documentId = workToBeUpdated.documentId
                }
            }


            work
        }

    }

}


