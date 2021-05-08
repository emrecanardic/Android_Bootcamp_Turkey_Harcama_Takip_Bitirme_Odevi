package com.emrecan.harcamatakip.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.ViewModel.AddPaymentViewModel
import com.canerkaya.harcamatakip.ViewModel.HomeViewModel
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.ViewModel.AddPaymentViewModel
import java.lang.IllegalArgumentException

class AddPaymentViewModelFactory(private val dataSource: PaymentDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPaymentViewModel::class.java)){
            return AddPaymentViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("")
    }
}