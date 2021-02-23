package com.kabirnayeem99.paymentpaid.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kabirnayeem99.paymentpaid.utils.Constants
import java.io.Serializable

/**
 * Represents a work
 * @property name the name of the work, in a format like student_name-work_name_shortcut
 * @property date submission day of the work
 * @property month the month of the work
 * @property year the year of the work
 */

@Entity(tableName = Constants.DB_TABLE)
data class Work(
        @ColumnInfo(name = "work_name")
        val name: String,

        @ColumnInfo(name = "submission_date")
        val date: Int,

        @ColumnInfo(name = "account_month")
        val month: Int,

        @ColumnInfo(name = "account_year")
        val year: Int,

        @ColumnInfo(name = "payment")
        val payment: String,

        @ColumnInfo(name = "student_name")
        val studentName: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
