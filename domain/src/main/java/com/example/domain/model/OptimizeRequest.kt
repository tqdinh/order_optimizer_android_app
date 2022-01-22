package com.example.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class OptimizeRequest
    (
    @SerializedName("data") val data: Data
)


data class Data(
    @SerializedName("shipping") val shipping: Int = 0,
    @SerializedName("orders") val orders: ArrayList<Order> = ArrayList(),
    @SerializedName("vouchers") val vouchers: ArrayList<Voucher> = ArrayList()
)
data class Order(
    @SerializedName("id") val id: String= UUID.randomUUID().toString(),
    @SerializedName("owner_name") val owner_name:String,
    @SerializedName("dish_name") val dish_name:String,
    @SerializedName("price") val price:Int
)
data class Voucher(
    @SerializedName("id") val id: String= UUID.randomUUID().toString(),
    @SerializedName("percent") val percent:Double,
    @SerializedName("range_from") val range_from:Int,
    @SerializedName("range_to") val range_to:Int,
    @SerializedName("max_applied") val max_applied:Double

)