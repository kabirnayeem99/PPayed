package com.kabirnayeem99.paymentpaid.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "works_db_table")
class Work(@field:ColumnInfo(name = "work_name") val name: String,
           @field:ColumnInfo(name = "submission_date") val date: String,
           @field:ColumnInfo(name = "account_month") val month: Int,
           @field:ColumnInfo(name = "account_year") val year: Int,
           @field:ColumnInfo(name = "payment") val payment: Int,
           @field:ColumnInfo(name = "student_name") val studentName: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}