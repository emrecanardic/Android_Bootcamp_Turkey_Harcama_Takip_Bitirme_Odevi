package com.emrecan.harcamatakip.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.Model.PaymentModel

@Database(entities = arrayOf(PaymentModel::class),version = 1)
abstract class DatabaseManager:RoomDatabase() {

    abstract fun paymentDao(): PaymentDao

    companion object{
        @Volatile var instance :DatabaseManager?=null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock,{
            instance ?: getDatabaseManager(context).also {
                instance = it
            }
        })

        fun getDatabaseManager(context: Context) = Room.databaseBuilder(context.applicationContext,DatabaseManager::class.java,"payments").build()
    }
}