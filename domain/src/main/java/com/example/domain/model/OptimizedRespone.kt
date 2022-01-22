package com.example.domain.model
import com.google.gson.annotations.SerializedName
data class OptimizedRespone(
    @SerializedName("bills") val bills: ArrayList<Bill> = ArrayList(),
    @SerializedName("total") val total:Double,
    @SerializedName("discounted") val discounted:Double,
    @SerializedName("shiping") val shiping:Double
)
data class Bill(
    @SerializedName("voucher") val voucher: String = "",
    @SerializedName("orders") val orders: ArrayList<String> = ArrayList()

)

