package com.example.domain.model

import java.util.*

data class VoucherStatus(
    val id:String,
    val apply_from:Int,
    val apply_to: Int=10240000,
    val percent_apply:Double,
    val maximum_quantity_apply:Double
)

{
    constructor(_apply_from:Int, _percentage_of_discount: Double, _maximum_quantity_apply:Double):this(UUID.randomUUID().toString(),_apply_from,10240000,_percentage_of_discount/100, _maximum_quantity_apply)
    {

    }

}

