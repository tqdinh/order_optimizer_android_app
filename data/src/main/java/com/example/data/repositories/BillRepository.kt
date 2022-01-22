package com.example.data.repositories

import com.example.data.datasource.BillDataSource
import com.example.domain.model.BillStatus
import com.example.domain.model.OptimizeRequest
import com.example.domain.model.OrderStatus
import com.example.domain.model.VoucherStatus
import org.json.JSONObject

class BillRepository(val billDataSource: BillDataSource) {

    suspend fun storeVouchers(voucherStatus: VoucherStatus)=
        billDataSource.storeVouchers(voucherStatus)

    suspend fun storeOrders(orderStatus: OrderStatus)=
        billDataSource.storeOrders(orderStatus)

    suspend fun updateVoucher( voucher_Status: VoucherStatus,
                               new_voucher_Status: VoucherStatus) =
        billDataSource.updateVoucher(voucher_Status, new_voucher_Status)

    suspend fun removeVoucher(voucherStatus: VoucherStatus)=
        billDataSource.removeVoucher(voucherStatus)

    suspend fun updateOrder(order_Status: OrderStatus, new_order_Status: OrderStatus)=
        billDataSource.updateOrder(order_Status,new_order_Status )

    suspend fun removeOrder(orderStatus: OrderStatus)=
        billDataSource.removeOrder(orderStatus)


    suspend fun getVouchers(billStatus: BillStatus)=
        billDataSource.getVouchers(billStatus)

    suspend fun getOrders(billStatus: BillStatus)=
        billDataSource.getOrders(billStatus)


    suspend fun loadOrders()=
        billDataSource.loadOrders()

    suspend fun loadVouchers()=
        billDataSource.loadVouchers()

    suspend fun requestCalculate(request: OptimizeRequest)=
        billDataSource.requestCalculate(request)

    suspend fun setShippingFee(fee:Double)=billDataSource.setShippingFee(fee)
    suspend fun getShippingFee():Double=billDataSource.getShippingFee()
}