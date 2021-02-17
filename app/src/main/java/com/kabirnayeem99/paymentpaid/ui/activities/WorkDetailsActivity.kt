package com.kabirnayeem99.paymentpaid.ui.activities

import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.utils.CustomUtils.currentDay
import com.kabirnayeem99.paymentpaid.utils.CustomUtils.currentMonth
import com.kabirnayeem99.paymentpaid.utils.CustomUtils.currentYear
import com.kabirnayeem99.paymentpaid.utils.CustomUtils.padMonth
import kotlinx.android.synthetic.main.activity_add_new_work.*
import java.util.*

class WorkDetailsActivity : AppCompatActivity() {
//    lateinit var work: Work
//    var date = String.format("%s-%s-%s", currentYear,
//            padMonth(currentMonth + 1),
//            padMonth(currentDay))
//    var paymentString: String? = null
//    var month = currentMonth + 1
//    var year = currentYear
//    var id = -1
//    private lateinit var workViewModel: WorkViewModel
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_new_work)
//        workViewModel = ViewModelProviders.of(this).get(workViewModel::class.java)
//        dpDate!!.setOnDateChangedListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
//            this.year = year
//            month = monthOfYear + 1
//            // month is returning a value less than the actual value, so magic number 1 is added
//            date = String.format("%s-%s-%s", year, padMonth(monthOfYear + 1),
//                    padMonth(dayOfMonth))
//        }
//
//        // manipulate views
//        intent = getIntent()
//        if (intent.hasExtra(EXTRA_ID)) {
//            Objects.requireNonNull(tilWorkName!!.editText)!!.setText(intent.getStringExtra(EXTRA_WORK_NAME))
//            tilWorkName!!.editText!!.setText(intent.getStringExtra(EXTRA_WORK_NAME))
//            Objects.requireNonNull(tilPayment!!.editText)!!.setText(intent.getIntExtra(EXTRA_PAYMENT, 0).toString())
//            Objects.requireNonNull(tilStudentName!!.editText)!!.setText(intent.getStringExtra(EXTRA_STUDENT_NAME))
//            val date = intent.getStringExtra(EXTRA_DATE)
//        }
//    }
//
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        saveToNoteDB()
//    }
//
//    private fun saveToNoteDB() {
//        // saves added note to the note database
//
//
//        // requireNonNull ensures that the field is guaranteed be non-null.
//        val workName = Objects.requireNonNull(tilWorkName!!.editText)!!.text.toString()
//        val studentName = Objects.requireNonNull(tilStudentName!!.editText)!!.text.toString()
//        paymentString = Objects.requireNonNull(tilPayment!!.editText)!!.text.toString()
//        if (workName.trim { it <= ' ' }.isNotEmpty() && !paymentString!!.trim { it <= ' ' }.isEmpty() && !studentName.trim { it <= ' ' }.isEmpty() && !date.trim { it <= ' ' }.isEmpty()) {
//            work = Work(workName, date, month, year, paymentString!!.toInt(), studentName)
//            id = intent!!.getIntExtra(EXTRA_ID, -1)
//            if (id == -1) {
//                Toast.makeText(this, "Work can't be update", Toast.LENGTH_SHORT).show()
//                workViewModel.insert(work)
//                Toast.makeText(this@WorkDetailsActivity, "Work is saved", Toast.LENGTH_SHORT).show()
//            } else {
//                work.id = id.toString()
//                workViewModel.update(work)
//            }
//        } else {
//            Toast.makeText(this, "Work was not saved", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun Work(id: String, name: String, date: Int, month: Int, year: Int, payment: String): Work {
//        TODO("Not yet implemented")
//    }
//
//    companion object {
//        const val EXTRA_ID = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_ID"
//        const val EXTRA_WORK_NAME = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_WORK_NAME"
//        const val EXTRA_PAYMENT = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_PAYMENT"
//        const val EXTRA_STUDENT_NAME = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_STUDENT_NAME"
//        const val EXTRA_DATE = "com.kabirnayeem99.paymentpaid.ui.activities.EXTRA_DATE"
//    }
}