package com.example.myoptimizationbill.framework


import android.util.Log
import com.example.data.datasource.BillDataSource
import com.example.data.utils.APIs
import com.example.domain.model.*
import com.example.myoptimizationbill.utils.Constants
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BillDataSourceImp : BillDataSource {

    var listVouchers: ArrayList<VoucherStatus> = ArrayList()
    var listOrders: ArrayList<OrderStatus> = ArrayList()
    var shipping:Double=0.0
    var listOfBill: ArrayList<BillStatus> = ArrayList()
    override suspend fun updateVoucher(
        voucher_Status: VoucherStatus,
        new_voucher_Status: VoucherStatus
    ) {

        listVouchers.remove(voucher_Status)
        listVouchers.add(new_voucher_Status)
    }


    override suspend fun removeVoucher(voucherStatus: VoucherStatus) {
        listVouchers.remove(voucherStatus)
    }

    override suspend fun updateOrder(order_Status: OrderStatus, new_order_Status: OrderStatus) {
        listOrders.remove(order_Status)
        listOrders.add(new_order_Status)
    }


    override suspend fun removeOrder(orderStatus: OrderStatus) {
        listOrders.remove(orderStatus)
    }

    override suspend fun getOrders(billStatus: BillStatus) {
        TODO("Not yet implemented")
    }

    override suspend fun getVouchers(billStatus: BillStatus) {
        TODO("Not yet implemented")
    }

    override suspend fun storeOrders(orderStatus: OrderStatus) {

        Log.d("ERRRRR++",orderStatus.toString())
        listOrders.add(orderStatus)
    }

    override suspend fun storeVouchers(voucherStatus: VoucherStatus) {
        listVouchers.add(voucherStatus)
    }

    override suspend fun loadOrders(): ArrayList<OrderStatus> {


        return listOrders
    }

    override suspend fun loadVouchers(): ArrayList<VoucherStatus> {
        return listVouchers
    }

    override suspend fun setShippingFee(fee: Double) {
        shipping=fee
    }

    override suspend fun getShippingFee(): Double {
        return shipping
    }

    override suspend fun requestCalculate(request: OptimizeRequest): Single<OptimizedRespone> {

        val httpClient = OkHttpClient.Builder()

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.serverDomain)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

        val retrofit = builder
            .client(
                httpClient.build()
            )
            .build()
//
//        var calculate=retrofit.create(APIs::class.java)
//        var respone:Single<OptimizedRespone> = calculate.requestCalculate(request)
//
//        return respone


        return Rx2AndroidNetworking.post("192.168.3.101:5000/dishes")
                .addBodyParameter(request)
                .build()
                .getObjectSingle(OptimizedRespone::class.java)



    }


}