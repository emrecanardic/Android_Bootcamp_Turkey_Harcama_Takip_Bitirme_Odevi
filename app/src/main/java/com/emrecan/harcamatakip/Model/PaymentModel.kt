package com.emrecan.harcamatakip.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "payments")
data class PaymentModel(

    @ColumnInfo(name = "paymentType")
    val paymentType:String,

    @ColumnInfo(name = "paymentName")
    val paymentName:String,

    @ColumnInfo(name = "tlCost")
    val tlCost:Int,

    @ColumnInfo(name = "dolarCost")
    val dolarCost:Int,

    @ColumnInfo(name = "sterlinCost")
    val sterlinCost:Int,

    @ColumnInfo(name = "euroCost")
    val euroCost:Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var primaryKey:Int = 0
}