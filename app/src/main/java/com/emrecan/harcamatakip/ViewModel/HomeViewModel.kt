package com.emrecan.harcamatakip.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canerkaya.harcamatakip.Data.DatabaseManager
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.canerkaya.harcamatakip.Model.UserModel
import com.canerkaya.harcamatakip.Util.CustomSharedPreferences
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.Model.UserModel
import com.emrecan.harcamatakip.Util.CustomSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(val database: PaymentDao, application: Application):AndroidViewModel(application),CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val user = MutableLiveData<UserModel>()
    val paymentList = MutableLiveData<ArrayList<PaymentModel>>()
    val checkedButton = MutableLiveData<String>()
    val tlTotal = MutableLiveData<Int>()
    val dolarTotal = MutableLiveData<Int>()
    val euroTotal = MutableLiveData<Int>()
    val sterlinTotal = MutableLiveData<Int>()

    init {
        tlTotal.value = 0
        dolarTotal.value = 0
        euroTotal.value = 0
        sterlinTotal.value = 0
        getUser()
        getPayments()

        checkedButton.value = "₺"
    }

    fun checkedButtonControl():Int{
        var result = 0
        when(checkedButton.value){
            "₺"->{result = tlTotal.value!!}
            "$"->{result = dolarTotal.value!!}
            "€"->{result = euroTotal.value!!}
            "£"->{result=sterlinTotal.value!!}
        }
        return result
    }


    fun getPayments(){
        launch {
            paymentList.value = ArrayList(database.getPaymentsFromDatabase())
            var tl =0
            var dolar =0
            var euro =0
            var sterlin =0
            for (i in 0..(paymentList.value!!.size-1)){
                tl = tl+ paymentList.value!![i].tlCost
                dolar = dolar+ paymentList.value!![i].dolarCost
                euro = euro+ paymentList.value!![i].euroCost
                sterlin = sterlin+ paymentList.value!![i].sterlinCost
            }
            tlTotal.value = tl
            dolarTotal.value = dolar
            euroTotal.value = euro
            sterlinTotal.value = sterlin

        }

    }

    fun getUser(){
        val userName = CustomSharedPreferences(getApplication()).getUserName().orEmpty()
        val userGender = CustomSharedPreferences(getApplication()).getUserGender().orEmpty()
        user.value = UserModel(userName,userGender)
    }



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}