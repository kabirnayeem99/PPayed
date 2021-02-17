package com.kabirnayeem99.paymentpaid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

@Database(entities = [Work::class], version = 1, exportSchema = false)
abstract class WorkDatabase : RoomDatabase() {

     abstract fun getWorkDao(): WorkDao

    companion object {
        private const val DB_NAME = "payment_paid_db"

        @Volatile
        private var instance: WorkDatabase? = null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        WorkDatabase::class.java,
                        DB_NAME
                ).build()

    }
}