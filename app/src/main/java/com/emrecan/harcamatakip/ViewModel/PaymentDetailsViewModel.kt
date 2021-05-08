package com.emrecan.harcamatakip.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.Model.PaymentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PaymentDetailsViewModel(val database: PaymentDao, application: Application): AndroidViewModel(application),CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val payment = MutableLiveData<PaymentModel>()
    val costType = MutableLiveData<String>()


    fun deletePayment(){
        launch {
            database.deletePayment(payment.value?.primaryKey!!)
        }
    }



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}