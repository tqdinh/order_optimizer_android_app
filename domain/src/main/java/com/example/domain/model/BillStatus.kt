package com.example.domain.model

import java.util.*
import kotlin.collections.ArrayList

data class BillStatus(

    var ids_voucher:ArrayList<String>,
    var ids_orders:ArrayList<String>
)
{

    val  id:String=UUID.randomUUID().toString()
}
