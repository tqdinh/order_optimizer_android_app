package com.example.domain.model

data class RegionalSalesData( var region:String,
var sales:Double)
{

    @JvmName("getRegion1")
    fun getRegion(): String? {
        return region
    }

    @JvmName("setRegion1")
    fun setRegion(region: String) {
        this.region = region
    }

    @JvmName("getSales1")
    fun getSales(): Double {
        return sales
    }

    @JvmName("setSales1")
    fun setSales(sales: Double) {
        this.sales = sales
    }
}
