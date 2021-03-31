package com.kabirnayeem99.paymentpaid.data.db.entities

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize
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
        private const val TAG = "Work"
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

                Log.e(TAG, "toWork: error converting work $e")
                //todo: implement crash analytics later
//                FirebaseCrashlytics.getInstance().log("Error converting user profile")
//                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
//                FirebaseCrashlytics.getInstance().recordException(e)
                return null
            }
        }

        fun createWork(){

        }
    }
}
