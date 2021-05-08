package com.emrecan.harcamatakip.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.ViewModel.HomeViewModel
import com.canerkaya.harcamatakip.ViewModel.PaymentDetailsViewModel
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.ViewModel.PaymentDetailsViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class PaymentDetailsViewModelFactory(private val dataSource: PaymentDao, private val application: Application):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentDetailsViewModel::class.java)){
            return PaymentDetailsViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("")
    }
}