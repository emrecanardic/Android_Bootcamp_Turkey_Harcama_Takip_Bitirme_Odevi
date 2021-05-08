package com.emrecan.harcamatakip.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RetrofitClient {
    companion object{
        val BASE_URL = "https://free.currconv.com/api/v7/convert/"
        fun getClient():ApiMethods{
            return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(ApiMethods::class.java)
        }
    }
}
