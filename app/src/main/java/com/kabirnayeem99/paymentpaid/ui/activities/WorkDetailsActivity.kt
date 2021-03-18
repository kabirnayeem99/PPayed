package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.enums.AccountStatus
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModel
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.android.synthetic.main.activity_add_new_work.*


/**
 * This Activity is for creating a new work or edit an existing work.
 * This [WorkDetailsActivity] extends [AppCompatActivity]
 */
class WorkDetailsActivity : AppCompatActivity() {
    private lateinit var workViewModel: WorkViewModel
    private lateinit var work: Work

    private var toBeUpdatedWork: Work? = null

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
        toBeUpdatedWork?.id?.let { fillTheFieldIfExistingWork(toBeUpdatedWork!!) }
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
        tilPayment.editText?.setText(workBeingProcessed.payment)
        dpDate.updateDate(workBeingProcessed.year, workBeingProcessed.month, workBeingProcessed.date)
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
            if (toBeUpdatedWork?.id != null) {
                workViewModel.update(work)
            } else {
                workViewModel.insert(work)
            }
            Toast.makeText(this, "Your work was saved.", Toast.LENGTH_SHORT).show()
        }
        super.onBackPressed()
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
                val workRepository = WorkRepository(WorkDatabase(this@WorkDetailsActivity), AccountStatus.OFFLINE)

                val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)

                workViewModel = ViewModelProvider(
                        this, workViewModelProviderFactory
                ).get(WorkViewModel::class.java)
            }
            false -> {
                val workRepository = WorkRepository(WorkDatabase(this@WorkDetailsActivity), AccountStatus.ONLINE)

                val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)

                workViewModel = ViewModelProvider(
                        this, workViewModelProviderFactory
                ).get(WorkViewModel::class.java)
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
            work = Work(workName, day, month, year, paymentAmount, studentName)

            toBeUpdatedWork?.let { workToBeUpdated ->
                work.id = workToBeUpdated.id
            }

            work
        }

    }

}


