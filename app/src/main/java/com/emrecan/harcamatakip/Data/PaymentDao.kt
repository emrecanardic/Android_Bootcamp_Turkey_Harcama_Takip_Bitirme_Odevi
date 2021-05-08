package com.emrecan.harcamatakip.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.Model.PaymentModel


@Dao
interface PaymentDao {
    @Insert(entity = PaymentModel::class)
    suspend fun insertPayment(model: PaymentModel):Long

    @Query("SELECT * FROM PAYMENTS ORDER BY primaryKey DESC ")
    suspend fun getPaymentsFromDatabase():List<PaymentModel>

    @Query("DELETE FROM PAYMENTS WHERE primaryKey = :key")
    suspend fun deletePayment(key:Int)
}