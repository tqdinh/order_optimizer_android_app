package com.example.data.datasource

import com.example.domain.model.*
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BillDataSource {
    suspend fun updateVoucher(voucher_Status: VoucherStatus,new_voucher_Status: VoucherStatus)
    suspend fun removeVoucher(voucherStatus: VoucherStatus)

    suspend fun updateOrder(order_Status: OrderStatus,new_order_Status: OrderStatus)
    suspend fun removeOrder(orderStatus: OrderStatus)

    suspend fun getOrders(billStatus: BillStatus)
    suspend fun getVouchers(billStatus: BillStatus)

    suspend fun storeOrders(orderStatus:OrderStatus)
    suspend fun storeVouchers(voucherStatus:VoucherStatus)

    suspend fun loadOrders():ArrayList<OrderStatus>
    suspend fun loadVouchers():ArrayList<VoucherStatus>

    suspend fun setShippingFee(fee:Double)
    suspend fun getShippingFee():Double

    suspend fun requestCalculate(@Body request: OptimizeRequest): Single<OptimizedRespone>
}