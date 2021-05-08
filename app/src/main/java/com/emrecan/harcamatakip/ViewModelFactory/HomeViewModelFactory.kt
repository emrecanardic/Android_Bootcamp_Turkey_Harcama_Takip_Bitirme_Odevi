package com.emrecan.harcamatakip.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.ViewModel.HomeViewModel
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.ViewModel.HomeViewModel
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val dataSource: PaymentDao, private val application: Application):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("")
    }
}