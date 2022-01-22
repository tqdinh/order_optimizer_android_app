package com.example.myoptimizationbill

import android.app.Application
import com.example.data.interactors.*
import com.example.data.repositories.BillRepository
import com.example.data.repositories.OrderRepository
import com.example.data.repositories.VoucherRepository
import com.example.myoptimizationbill.framework.BillDataSourceImp
import com.example.myoptimizationbill.framework.Interactors
import com.example.myoptimizationbill.framework.MyViewModelFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    lateinit var useCasese: Interactors
    override fun onCreate() {
        super.onCreate()
        var  billRepository: BillRepository= BillRepository(BillDataSourceImp())
        var orderRepository: OrderRepository
        var voucherRepository: VoucherRepository

        useCasese= Interactors(updateOrder = updateOrder(billRepository)
        ,removeOrder = RemoveOrder(billRepository)
        ,updateVoucher = UpdateVoucher(billRepository)
        ,removeVoucher = RemoveVoucher(billRepository)
        ,getVouchers = GetVouchers(billRepository)
        ,getOrders = GetOrders(billRepository)
        ,storeOrders = StoreOrders(billRepository)
        ,storeVouchers = StoreVouchers(billRepository)
        ,loadOrders = LoadOrders(billRepository)
        ,loadVouchers = LoadVouchers(billRepository)
        ,requestCalculate = RequestCalculate(billRepository)
        ,setShipping = SetShippingFee(billRepository)
        ,getShippingFee = GetShippingFee(billRepository)

        )

        MyViewModelFactory.inject(this, useCasese)
    }


}