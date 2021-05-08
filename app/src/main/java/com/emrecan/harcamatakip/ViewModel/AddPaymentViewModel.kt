package com.emrecan.harcamatakip.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.canerkaya.harcamatakip.Data.PaymentDao
import com.canerkaya.harcamatakip.Model.*
import com.canerkaya.harcamatakip.Service.RetrofitClient
import com.canerkaya.harcamatakip.Util.CustomSharedPreferences
import com.emrecan.harcamatakip.Data.PaymentDao
import com.emrecan.harcamatakip.Model.*
import com.emrecan.harcamatakip.Service.RetrofitClient
import com.emrecan.harcamatakip.Util.CustomSharedPreferences
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.math.cos

class AddPaymentViewModel(val database: PaymentDao, application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val api = RetrofitClient.getClient()

    val paymentName = MutableLiveData<String>()
    val paymentCost = MutableLiveData<Int>()
    val paymentType = MutableLiveData<String>()
    val costType = MutableLiveData<String>()
    val tlCost = MutableLiveData<Int>()
    val dolarCost = MutableLiveData<Int>()
    val euroCost = MutableLiveData<Int>()
    val gbpCost = MutableLiveData<Int>()
    val loading = MutableLiveData<String>()

    init {
        loading.value = "wait"
    }



    private fun savePayment(model: PaymentModel){
        launch {
            try {
                val long = database.insertPayment(model)
                model.primaryKey = long.toInt()
                loading.value = "Done"
            }catch (e:Exception){
                loading.value = "Error"
            }

        }
    }

    fun trySelected(cost:Int){
        loading.value = "loading"
        tlCost.value = cost

            api.trtoUsd().enqueue(object :Callback<TrToUsdModel>{
                override fun onResponse(call: Call<TrToUsdModel>, response: Response<TrToUsdModel>) {
                    response.let {
                        CustomSharedPreferences(getApplication()).setTlToUsd(it.body()?.TRY_USD!!.toInt())
                    }
                    dolarCost.value = response.body()?.TRY_USD?.times(cost)?.toInt()
                    trToEuro(cost)
                }

                override fun onFailure(call: Call<TrToUsdModel>, t: Throwable) {
                    dolarCost.value = CustomSharedPreferences(getApplication()).getTlToUsd()?.times(cost)
                    if (dolarCost.value != 0){
                        trToEuro(cost)
                    }else{
                        loading.value = "Error"
                    }
                }
            })




    }
    private fun trToEuro(cost: Int){
        api.trToEur().enqueue(object :Callback<TrToEurModel>{
            override fun onResponse(call: Call<TrToEurModel>, response: Response<TrToEurModel>) {
                response.body()?.TRY_EUR?.let {
                    CustomSharedPreferences(getApplication()).setTlToEur(it.toInt())
                }
                euroCost.value = response.body()?.TRY_EUR?.times(cost)?.toInt()
                trToGbp(cost)
            }

            override fun onFailure(call: Call<TrToEurModel>, t: Throwable) {
                euroCost.value = CustomSharedPreferences(getApplication()).getTlToEur()?.times(cost)
                if (euroCost.value !=0){
                    trToGbp(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    private fun trToGbp(cost: Int){
        api.trToGbp().enqueue(object :Callback<TrToGbpModel>{
            override fun onResponse(call: Call<TrToGbpModel>, response: Response<TrToGbpModel>) {
                response.body()?.TRY_GBP?.let {
                    CustomSharedPreferences(getApplication()).setTlToGbp(it.toInt())
                }
                gbpCost.value = response.body()?.TRY_GBP?.times(cost)?.toInt()
                savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
            }

            override fun onFailure(call: Call<TrToGbpModel>, t: Throwable) {
                gbpCost.value = CustomSharedPreferences(getApplication()).getTlToGbp()?.times(cost)
                if (gbpCost.value != 0){
                    savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
                }else{
                    loading.value = "Error"
                }
            }
        })
    }

    fun gbpSelected(cost: Int){
        loading.value = "loading"
        gbpCost.value = cost
        api.gbpToTr().enqueue(object :Callback<GbpToTrModel>{
            override fun onResponse(call: Call<GbpToTrModel>, response: Response<GbpToTrModel>) {
                response.body()?.GBP_TRY?.let {
                    CustomSharedPreferences(getApplication()).setGbpToTl(it.toInt())
                }
                tlCost.value = response.body()?.GBP_TRY?.times(cost)?.toInt()
                gbpToUsd(cost)
            }

            override fun onFailure(call: Call<GbpToTrModel>, t: Throwable) {
                tlCost.value = CustomSharedPreferences(getApplication()).getGbpToTr()?.times(cost)
                if (tlCost.value != 0){
                    gbpToUsd(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    private fun gbpToUsd(cost: Int){
        api.gbpToUsd().enqueue(object :Callback<GbpToUsdModel>{
            override fun onResponse(call: Call<GbpToUsdModel>, response: Response<GbpToUsdModel>) {
                response.body()?.GBP_USD?.let {
                    CustomSharedPreferences(getApplication()).setGbpToUsd(it.toInt())
                }
                dolarCost.value = response.body()?.GBP_USD?.times(cost)?.toInt()
                gbpToEur(cost)
            }

            override fun onFailure(call: Call<GbpToUsdModel>, t: Throwable) {
                dolarCost.value = CustomSharedPreferences(getApplication()).getGbpToUsd()?.times(cost)
                if (dolarCost.value != 0){
                    gbpToEur(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })

    }
    private fun gbpToEur(cost: Int){
        api.gbpToEur().enqueue(object :Callback<GbpToEurModel>{
            override fun onResponse(call: Call<GbpToEurModel>, response: Response<GbpToEurModel>) {
                response.body()?.GBP_EUR?.let {
                    CustomSharedPreferences(getApplication()).setGbpToEur(it.toInt())
                }
                euroCost.value = response.body()?.GBP_EUR?.times(cost)?.toInt()
                savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
            }

            override fun onFailure(call: Call<GbpToEurModel>, t: Throwable) {
                euroCost.value = CustomSharedPreferences(getApplication()).getGbpToEur()?.times(cost)
                if (euroCost.value != 0){
                    savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
                }else{
                    loading.value = "Error"
                }
            }

        })
    }

    fun euroSelected(cost: Int){
        loading.value = "loading"
        euroCost.value = cost
        api.eurToTr().enqueue(object :Callback<EurToTrModel>{
            override fun onResponse(call: Call<EurToTrModel>, response: Response<EurToTrModel>) {
                response.body()?.EUR_TRY?.let {
                    CustomSharedPreferences(getApplication()).setEurToTl(it.toInt())
                }
                tlCost.value = response.body()?.EUR_TRY?.times(cost)?.toInt()
                eurToUsd(cost)
            }

            override fun onFailure(call: Call<EurToTrModel>, t: Throwable) {
                tlCost.value = CustomSharedPreferences(getApplication()).getEurToTr()?.times(cost)
                if (tlCost.value != 0){
                    eurToUsd(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })

    }
    private fun eurToUsd(cost: Int){
        api.eurToUsd().enqueue(object :Callback<EurToUsdModel>{
            override fun onResponse(call: Call<EurToUsdModel>, response: Response<EurToUsdModel>) {
                response.body()?.EUR_USD?.let {
                    CustomSharedPreferences(getApplication()).setEurToUsd(it.toInt())
                }
                dolarCost.value = response.body()?.EUR_USD?.times(cost)?.toInt()
                eurToGbp(cost)
            }

            override fun onFailure(call: Call<EurToUsdModel>, t: Throwable) {
                dolarCost.value = CustomSharedPreferences(getApplication()).getEurToUsd()?.times(cost)
                if (dolarCost.value != 0){
                    eurToGbp(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    private fun eurToGbp(cost: Int){
        api.eurToGbp().enqueue(object :Callback<EurToGbpModel>{
            override fun onResponse(call: Call<EurToGbpModel>, response: Response<EurToGbpModel>) {
                response.body()?.EUR_GBP?.let {
                    CustomSharedPreferences(getApplication()).setEurToGbp(it.toInt())
                }
                gbpCost.value = response.body()?.EUR_GBP?.times(cost)?.toInt()
                savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
            }

            override fun onFailure(call: Call<EurToGbpModel>, t: Throwable) {
                gbpCost.value = CustomSharedPreferences(getApplication()).getEurToGbp()?.times(cost)
                if (gbpCost.value != 0){
                    savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    fun usdSelected(cost: Int){
        loading.value = "loading"
        dolarCost.value = cost
        api.usdToTr().enqueue(object :Callback<UsdToTrModel>{
            override fun onResponse(call: Call<UsdToTrModel>, response: Response<UsdToTrModel>) {
                response.body()?.USD_TRY?.let {
                    CustomSharedPreferences(getApplication()).setUsdToTl(it.toInt())
                }
                tlCost.value = response.body()?.USD_TRY?.times(cost)?.toInt()
                usdToGbp(cost)
            }

            override fun onFailure(call: Call<UsdToTrModel>, t: Throwable) {
                tlCost.value = CustomSharedPreferences(getApplication()).getUsdToTr()?.times(cost)
                if (tlCost.value !=0){
                    usdToGbp(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    private fun usdToGbp(cost: Int){
        api.usdToGbp().enqueue(object :Callback<UsdToGbpModel>{
            override fun onResponse(call: Call<UsdToGbpModel>, response: Response<UsdToGbpModel>) {
                response.body()?.USD_GBP?.let {
                    CustomSharedPreferences(getApplication()).setUsdToGbp(it.toInt())
                }
                gbpCost.value = response.body()?.USD_GBP?.times(cost)?.toInt()
                usdToEur(cost)
            }

            override fun onFailure(call: Call<UsdToGbpModel>, t: Throwable) {
                gbpCost.value = CustomSharedPreferences(getApplication()).getUsdToGbp()?.times(cost)
                if (gbpCost.value != 0){
                    usdToEur(cost)
                }else{
                    loading.value = "Error"
                }
            }

        })
    }
    private fun usdToEur(cost: Int){
        api.usdToEur().enqueue(object :Callback<UsdToEurModel>{
            override fun onResponse(call: Call<UsdToEurModel>, response: Response<UsdToEurModel>) {
                response.body()?.USD_EUR?.let {
                    CustomSharedPreferences(getApplication()).setUsdToEur(it.toInt())
                }
                euroCost.value = response.body()?.USD_EUR?.times(cost)?.toInt()
                savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
            }

            override fun onFailure(call: Call<UsdToEurModel>, t: Throwable) {
                euroCost.value = CustomSharedPreferences(getApplication()).getUsdToEur()?.times(cost)
                if (euroCost.value != 0){
                    savePayment(PaymentModel(paymentType.value!!,paymentName.value!!,tlCost.value!!,dolarCost.value!!,gbpCost.value!!,euroCost.value!!))
                }else{
                    loading.value = "Error"
                }
            }
        })
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}