package com.kabirnayeem99.paymentpaid.domain.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import java.lang.Exception

/**
 * Represents a work
 * @property name the name of the work, in a format like student_name-work_name_shortcut
 * @property day submission day of the work
 * @property month the month of the work
 * @property year the year of the work
 */
@Parcelize
data class Work(
    var documentId: String?,
    var name: String,
    var day: Long,
    var month: Long,
    var year: Long,
    var payment: Long,
    var studentName: String
) : Parcelable {


    companion object {
        fun DocumentSnapshot.toWork(): Work? {
            try {
                val name = getString("name")!!
                val day = getLong("day")!!
                val month = getLong("month")!!
                val year = getLong("year")!!
                val payment = getLong("payment")!!
                val studentName = getString("studentName")!!

                return Work(id, name, day, month, year, payment, studentName)
            } catch (e: Exception) {
                Timber.e("toWork: error converting work due to $e")
                return null
            }
        }
    }
}
