package com.kabirnayeem99.paymentpaid.ui.activities

import android.os.Build
import android.os.Bundle
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_work)
        setUpViewModel()


    }

    override fun onBackPressed() {

        createWork()?.let { work ->
            workViewModel.insert(work)
            super.onBackPressed()
        }

        Toast.makeText(this, "Your work wasn't saved.", Toast.LENGTH_SHORT).show()

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

        var month = 1
        var year = 2021
        var day = 1

        dpDate?.let { dpDate ->
            month = dpDate.month
            year = dpDate.year
            day = dpDate.dayOfMonth
        }

        work = Work(workName, day, month, year, paymentAmount, studentName)

        return work
    }

}


