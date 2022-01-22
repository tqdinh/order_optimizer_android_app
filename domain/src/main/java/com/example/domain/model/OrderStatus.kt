package com.example.domain.model

import java.util.*

data class OrderStatus(
    val id:String,
    val dish_name:String,
    val price:Int,
    val owner_name:String
)
{
    constructor(_name:String,_price:Int,_owner_name:String):this(UUID.randomUUID().toString(),_name,_price, _owner_name)
    {

    }



}
