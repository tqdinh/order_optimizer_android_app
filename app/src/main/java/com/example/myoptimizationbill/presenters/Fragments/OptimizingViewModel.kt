package com.example.myoptimizationbill.presenters.Fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.data.utils.APIs
import com.example.domain.model.*
import com.example.myoptimizationbill.framework.Interactors
import com.example.myoptimizationbill.framework.MyViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class OptimizingViewModel(application: Application, interactors: Interactors) :
    MyViewModel(application, interactors), CoroutineScope {


    val chartLiveData: MutableLiveData<Pair<ArrayList<Pair<Double, String>>, Boolean>> =
        MutableLiveData()


    private val orders: ArrayList<OrderStatus> = ArrayList()
    val orderLiveData: MutableLiveData<ArrayList<OrderStatus>> = MutableLiveData()

    private val vouchers: ArrayList<VoucherStatus> = ArrayList()
    val vouchersLiveData: MutableLiveData<ArrayList<VoucherStatus>> = MutableLiveData()

    private lateinit var respone: OptimizedRespone
    val responseLiveData: MutableLiveData<OptimizedRespone> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()


    var ship: Double = 0.0
    var vouchersParam: ArrayList<Voucher> = ArrayList()
    var ordersParam: ArrayList<Order> = ArrayList()

    fun getOrder(order_id: String): OrderStatus? {
        var orderStatus: OrderStatus? = null
        for (order in orders) {
            if (order_id.equals(order.id)) {
                orderStatus = order
                break
            }
        }
        return orderStatus
    }

    fun getVoucher(voucher_id: String): VoucherStatus? {
        var voucherStatus: VoucherStatus? = null
        for (voucher in vouchers) {
            if (voucher_id.equals(voucher.id)) {
                voucherStatus = voucher
                break
            }
        }
        return voucherStatus
    }


    fun calculateChartData(respone: OptimizedRespone) {

        Log.d("OPTIMIZE",respone.toString())

        var billTotalSpend: Double = 0.0
        var billTotalSpendAfterDiscounted: Double = 0.0
        var billTotalDicounted: Double = 0.0
        var billShippingFee: Double = 0.0

        billTotalSpend = respone.total
        billTotalDicounted = respone.discounted

        billShippingFee=respone.shiping

        billTotalSpendAfterDiscounted = billTotalSpend - billTotalDicounted-billShippingFee


        var payable: Pair<Double, String> = Pair(billTotalSpendAfterDiscounted, "Payable amount")
        var rebate: Pair<Double, String> = Pair(billTotalDicounted, "Rebate")
        var shipping: Pair<Double, String> = Pair(billShippingFee, "Shipping")

        var result: ArrayList<Pair<Double, String>> = ArrayList()
        result.add(payable)
        result.add(rebate)
        result.add(shipping)



        chartLiveData.postValue(Pair(result, false))
    }

//    fun getListOrders() {
//        launch {
//            orderLiveData.postValue(interactors.loadOrders())
//        }
//    }

//    fun getListVoucher() {
//        GlobalScope.launch {
//            vouchersLiveData.postValue(interactors.loadVouchers())
//        }
//    }

    fun convertFromModelToRequestModel() {

        for (my_order in orders) {

            ordersParam.add(
                Order(
                    id = my_order.id,
                    dish_name = my_order.dish_name,
                    price = my_order.price,
                    owner_name = my_order.owner_name
                )
            )

        }

        for (my_voucher in vouchers) {
            vouchersParam.add(
                Voucher(
                    my_voucher.id,
                    percent = my_voucher.percent_apply,
                    range_from = my_voucher.apply_from,
                    range_to = 10240000,
                    max_applied = 0.001 + my_voucher.maximum_quantity_apply
                )
            )
        }
    }

    fun calculate() {
        launch {
            orders.clear()
            vouchers.clear()

            ordersParam.clear()
            vouchersParam.clear()

            orders.addAll(interactors.loadOrders())

            var k=interactors.loadOrders()
            Log.d("ERRRRR", k.toString())
            vouchers.addAll(interactors.loadVouchers())

            orderLiveData.postValue(orders)
            vouchersLiveData.postValue(vouchers)

            Log.d("ERRRRR", orders.toString())

            try {
                convertFromModelToRequestModel()
            } catch (e: Exception) {
                Log.d("ERRRRR", e.toString())
            }
            ship = interactors.getShippingFee()
            var request = OptimizeRequest(Data(ship.toInt(), ordersParam, vouchersParam))
            calculate(request)
        }
    }


    fun calculate(request: OptimizeRequest) {

        var api = APIs.create().requestCalculate(request)
        api.enqueue(object : retrofit2.Callback<OptimizedRespone> {
            override fun onResponse(
                call: Call<OptimizedRespone>,
                response: Response<OptimizedRespone>
            ) {
                var optimizedRespone: OptimizedRespone? = response.body()
                if (null != optimizedRespone) {
                    responseLiveData.postValue(optimizedRespone!!)
                    calculateChartData(optimizedRespone)
                }
            }

            override fun onFailure(call: Call<OptimizedRespone>, t: Throwable) {
                Log.d("DOMAIN_CONFIG", "exc" + t.message.toString())

            }
        })


//                var api=APIs.create().postLoc()
//        api.enqueue(object:retrofit2.Callback<JsonObject>{
//            override fun onResponse(
//                call: Call<JsonObject>,
//                response: Response<JsonObject>
//            ) {
//                var v=response.body()
//                Log.d("DOMAIN_CONFIG",v.toString())
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                Log.d("DOMAIN_CONFIG",t.message.toString())
//                t.message
//            }
//        })


//        var calculate=retrofit.create(APIs::class.java)
//        GlobalScope.launch(Dispatchers.Main) {
//            var postRequest = calculate.requestCalculate(request)
//            var postRespone=postRequest.await()
//            if ( postRespone.isSuccessful)
//            {
//                var opt=postRespone.body()
//                var bills =opt?.bills
//            }
//        }

//        respone.subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe ({
//                it.bills
//            }){
//                it.message
//            }
//

//        GlobalScope.launch {
//
//            interactors.requestCalculate(request)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    it.bills
//                }) {
//                    it.message
//                }
//
//
//        }
    }

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

}