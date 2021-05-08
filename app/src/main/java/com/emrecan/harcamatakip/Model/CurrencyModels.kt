package com.emrecan.harcamatakip.Model

import com.google.gson.annotations.SerializedName

data class TrToUsdModel(
    @SerializedName("TRY_USD")
    val TRY_USD:Double?
)
data class TrToEurModel(
        @SerializedName("TRY_EUR")
        val TRY_EUR:Double?
)
data class TrToGbpModel(
        @SerializedName("TRY_GBP")
        val TRY_GBP:Double?
)


data class UsdToTrModel(
        @SerializedName("USD_TRY")
        val USD_TRY:Double
)
data class UsdToEurModel(
        @SerializedName("USD_EUR")
        val USD_EUR:Double
)
data class UsdToGbpModel(
        @SerializedName("USD_GBP")
        val USD_GBP:Double
)


data class EurToTrModel(
        @SerializedName("EUR_TRY")
        val EUR_TRY:Double
)
data class EurToUsdModel(
        @SerializedName("EUR_USD")
        val EUR_USD:Double
)
data class EurToGbpModel(
        @SerializedName("EUR_GBP")
        val EUR_GBP:Double
)

data class GbpToTrModel(
        @SerializedName("GBP_TRY")
        val GBP_TRY:Double
)
data class GbpToEurModel(
        @SerializedName("GBP_EUR")
        val GBP_EUR:Double
)
data class GbpToUsdModel(
        @SerializedName("GBP_USD")
        val GBP_USD:Double
)


