package com.example.myoptimizationbill.framework

import com.example.data.interactors.*

data class Interactors(
    val updateOrder: updateOrder,
    val removeOrder: RemoveOrder,
    val updateVoucher: UpdateVoucher,
    val removeVoucher: RemoveVoucher,
    val getOrders: GetOrders,
    val getVouchers: GetVouchers,
    val storeVouchers: StoreVouchers,
    val storeOrders: StoreOrders,
    val loadVouchers:LoadVouchers,
    val loadOrders:LoadOrders,
    val requestCalculate:RequestCalculate,
    val setShipping:SetShippingFee,
    val getShippingFee: GetShippingFee

)
