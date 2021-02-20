package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_add_new_work.*

class WorkDetailsActivity : AppCompatActivity() {
    private lateinit var workViewModel: WorkViewModel
    private lateinit var work: Work

    private var toBeUpdatedWork: Work? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PaymentPaid)
        setContentView(R.layout.activity_add_new_work)
        setUpViewModel()

        val intent: Intent = intent

        val bundle = intent.extras

        toBeUpdatedWork = bundle?.getSerializable("work") as? Work

        toBeUpdatedWork?.id?.let { updateWork(toBeUpdatedWork!!) }

    }

    private fun updateWork(workBeingProcessed: Work) {
        tilWorkName.editText?.setText(workBeingProcessed.name)
        tilStudentName.editText?.setText(workBeingProcessed.studentName)
        tilPayment.editText?.setText(workBeingProcessed.payment)
        dpDate.updateDate(workBeingProcessed.year, workBeingProcessed.month, workBeingProcessed.date)
    }

    override fun onBackPressed() {

        createWork()?.let { work ->
            if (toBeUpdatedWork?.id != null) {
                Log.d(TAG, "onBackPressed: ${toBeUpdatedWork.toString()}")
                workViewModel.update(work)
            } else {
                workViewModel.insert(work)
            }
        }
        Toast.makeText(this, "Your work wasn't saved.", Toast.LENGTH_SHORT).show()
        super.onBackPressed()

    }


    private fun setUpViewModel() {
        val workRepository = WorkRepository(WorkDatabase(this))

        val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)

        workViewModel = ViewModelProvider(
                this, workViewModelProviderFactory
        ).get(WorkViewModel::class.java)
    }

    private fun createWork(): Work? {
        val workName: String = tilWorkName.editText?.text.toString()
        val studentName: String = tilStudentName.editText?.text.toString()
        val paymentAmount: String = tilPayment.editText?.text.toString()

        var month = 1 + 1
        var year = 2021
        var day = 1

        dpDate?.let { dpDate ->
            month = dpDate.month
            year = dpDate.year
            day = dpDate.dayOfMonth
        }

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

    companion object {
        private const val TAG = "WorkDetailsActivity"
    }

}


