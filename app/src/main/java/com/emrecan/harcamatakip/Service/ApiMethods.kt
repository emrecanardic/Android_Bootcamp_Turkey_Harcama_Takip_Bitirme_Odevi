package com.emrecan.harcamatakip.Service

import com.canerkaya.harcamatakip.Model.*
import com.emrecan.harcamatakip.Model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiMethods {

    @GET("?q=EUR_TRY&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun eurToTr():Call<EurToTrModel>

    @GET("?q=EUR_USD&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun eurToUsd():Call<EurToUsdModel>

    @GET("?q=EUR_GBP&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun eurToGbp():Call<EurToGbpModel>

    @GET("?q=TRY_EUR&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun trToEur():Call<TrToEurModel>

    @GET("?q=TRY_USD&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun trtoUsd():Call<TrToUsdModel>

    @GET("?q=TRY_GBP&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun trToGbp():Call<TrToGbpModel>

    @GET("?q=USD_TRY&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun usdToTr():Call<UsdToTrModel>

    @GET("?q=USD_EUR&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun usdToEur():Call<UsdToEurModel>

    @GET("?q=USD_GBP&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun usdToGbp():Call<UsdToGbpModel>

    @GET("?q=GBP_TRY&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun gbpToTr():Call<GbpToTrModel>

    @GET("?q=GBP_EUR&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun gbpToEur():Call<GbpToEurModel>

    @GET("?q=GBP_USD&compact=ultra&apiKey=e95c24e1abf8f4b63baa")
     fun gbpToUsd():Call<GbpToUsdModel>
}