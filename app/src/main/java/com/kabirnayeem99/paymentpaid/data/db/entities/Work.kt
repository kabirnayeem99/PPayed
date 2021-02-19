package com.kabirnayeem99.paymentpaid.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "works_db_table")
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
