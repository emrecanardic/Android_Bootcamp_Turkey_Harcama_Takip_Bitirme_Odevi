package com.emrecan.harcamatakip.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class CustomSharedPreferences {
    private val FIRST = "first"
    private val USER_NAME="user_name"
    private val GENDER = "gender"
    private val tlToUsd = "TL_USD"
    private val tlToEur = "TL_EUR"
    private val tlToGbp = "TL_GBP"
    private val usdToTr = "USD_TL"
    private val usdToEur = "USD_EUR"
    private val usdToGbp = "USD_GBP"
    private val eurToTr = "EUR_TL"
    private val eurToUsd = "EUR_USD"
    private val eurToGbp = "EUR_GBP"
    private val gbpToTr = "GBP_TL"
    private val gbpToEur = "GBP_EUR"
    private val gbpToUsd = "GBP_USD"
    companion object {
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: makeCustomSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }
    fun setTlToUsd(usd:Int){
        sharedPreferences?.edit {
            putInt(tlToUsd,usd)
        }
    }
    fun setTlToEur(eur: Int){
        sharedPreferences?.edit {
            putInt(tlToEur,eur)
        }
    }
    fun setTlToGbp(gbp: Int){
        sharedPreferences?.edit {
            putInt(tlToGbp,gbp)
        }
    }
    fun getTlToUsd():Int?{
        return sharedPreferences?.getInt(tlToUsd,0)
    }
    fun getTlToEur():Int?{
        return sharedPreferences?.getInt(tlToEur,0)
    }
    fun getTlToGbp():Int?{
        return sharedPreferences?.getInt(tlToGbp,0)
    }

    fun setUsdToTl(tl:Int){
        sharedPreferences?.edit(){
            putInt(usdToTr,tl)
        }
    }
    fun setUsdToEur(eur: Int){
        sharedPreferences?.edit {
            putInt(usdToEur,eur)
        }
    }
    fun setUsdToGbp(gbp: Int){
        sharedPreferences?.edit {
            putInt(usdToGbp,gbp)
        }
    }
    fun getUsdToTr():Int?{
        return sharedPreferences?.getInt(usdToTr,0)
    }
    fun getUsdToEur():Int?{
        return sharedPreferences?.getInt(usdToEur,0)
    }
    fun getUsdToGbp():Int?{
        return sharedPreferences?.getInt(usdToGbp,0)
    }

    fun setEurToTl(tl: Int){
        sharedPreferences?.edit(){
            putInt(eurToTr,tl)
        }
    }
    fun setEurToUsd(usd: Int){
        sharedPreferences?.edit(){
            putInt(eurToUsd,usd)
        }
    }
    fun setEurToGbp(gbp: Int){
        sharedPreferences?.edit {
            putInt(eurToGbp,gbp)
        }
    }
    fun getEurToTr():Int?{
        return sharedPreferences?.getInt(eurToTr,0)
    }
    fun getEurToUsd():Int?{
        return sharedPreferences?.getInt(eurToUsd,0)
    }
    fun getEurToGbp():Int?{
        return sharedPreferences?.getInt(eurToGbp,0)
    }

    fun setGbpToTl(tl: Int){
        sharedPreferences?.edit(){
            putInt(gbpToTr,tl)
        }
    }
    fun setGbpToUsd(usd: Int){
        sharedPreferences?.edit {
            putInt(gbpToUsd,usd)
        }
    }
    fun setGbpToEur(eur: Int){
        sharedPreferences?.edit {
            putInt(gbpToEur,eur)
        }
    }
    fun getGbpToTr():Int?{
        return sharedPreferences?.getInt(gbpToTr,0)
    }
    fun getGbpToEur():Int?{
        return sharedPreferences?.getInt(gbpToEur,0)
    }
    fun getGbpToUsd():Int?{
        return sharedPreferences?.getInt(gbpToUsd,0)
    }
    fun saveUser(name:String,gender:String){
        sharedPreferences?.edit(){
            putString(USER_NAME,name)
            putString(GENDER,gender)
        }
    }
    fun getUserName():String?{
        return sharedPreferences?.getString(USER_NAME,"")
    }
    fun getUserGender():String?{
        return sharedPreferences?.getString(GENDER,"")
    }
    fun isFirst():String?{
        return sharedPreferences?.getString(FIRST,"")
    }
    fun setFirst(){
        sharedPreferences?.edit {
            putString(FIRST,"not")
        }
    }

}