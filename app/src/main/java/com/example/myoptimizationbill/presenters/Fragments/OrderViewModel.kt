package com.example.myoptimizationbill.presenters.Fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Order
import com.example.domain.model.OrderStatus
import com.example.myoptimizationbill.framework.Interactors
import com.example.myoptimizationbill.framework.MyViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OrderViewModel(application: Application, interactors: Interactors) :
    MyViewModel(application, interactors), CoroutineScope {
    private val orders: ArrayList<OrderStatus> = ArrayList()
    val orderLiveData: MutableLiveData<ArrayList<OrderStatus>> = MutableLiveData()

    fun removeOrder(orderStatus: OrderStatus) {
        orders.remove(orderStatus)
        launch {
            interactors.removeOrder(orderStatus)
        }
        orderLiveData.postValue(orders)

    }

    fun addOrder(orderStatus: OrderStatus) {
        orders.add(orderStatus)
        orderLiveData.postValue(orders)
        launch {
            Log.d("ERRRRR+++",orderStatus.toString())
            interactors.storeOrders(orderStatus)
        }
    }

    fun updateOrder(oldOrder: OrderStatus, newOrder: OrderStatus) {
        var oldIndex = orders.indexOf(oldOrder)
        orders.remove(oldOrder)
        orders.add(oldIndex, newOrder)
        orderLiveData.postValue(orders)
        launch {
            interactors.updateOrder(oldOrder, newOrder)
        }

    }

    fun test() {

        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com tron", _price = 49000, _owner_name = "COM"))

        addOrder(OrderStatus(_name = "com trung cuon", _price = 45000, _owner_name = "COM"))
        addOrder(OrderStatus(_name = "com trung cuon", _price = 45000, _owner_name = "COM"))

        addOrder(OrderStatus(_name = "kimbap bo", _price = 35000, _owner_name = "AN"))
        addOrder(OrderStatus(_name = "kimbap bo", _price = 35000, _owner_name = "AN"))

        addOrder(OrderStatus(_name = "toboki truyen thong", _price = 35000, _owner_name = "AN"))
        addOrder(OrderStatus(_name = "toboki truyen thong", _price = 35000, _owner_name = "AN"))

        addOrder(OrderStatus(_name = "toboki pho mai", _price = 49000, _owner_name = "AN"))
        addOrder(OrderStatus(_name = "toboki pho mai", _price = 49000, _owner_name = "AN"))
        addOrder(OrderStatus(_name = "toboki pho mai", _price = 49000, _owner_name = "AN"))

        addOrder(OrderStatus(_name = "kim bap chien xu", _price = 49000, _owner_name = "AN"))
        addOrder(OrderStatus(_name = "kim bap chien xu", _price = 49000, _owner_name = "AN"))

//        addOrder(OrderStatus(_name = "Nuoc Cam", _price = 35000, _owner_name = "AN"))
//        addOrder(OrderStatus(_name = "Sau rieng", _price = 42000, _owner_name = "Han"))
//        addOrder(OrderStatus(_name = "Nuoc Tao", _price = 100, _owner_name = "Quynh"))
//        addOrder(OrderStatus(_name = "Cam Vat", _price = 70, _owner_name = "Dat"))
//        addOrder(OrderStatus(_name = "Nuoc viet quoc", _price = 70, _owner_name = "Ha"))
//
//        addOrder(OrderStatus(_name = "Sau rieng", _price = 2, _owner_name = "Han"))
//        addOrder(OrderStatus(_name = "Nuoc Tao", _price = 10, _owner_name = "Quynh"))
//        addOrder(OrderStatus(_name = "Cam Vat", _price = 7, _owner_name = "Dat"))
//        addOrder(OrderStatus(_name = "Nuoc viet quoc", _price = 150, _owner_name = "Ha"))

    }

    fun setShippingFee(fee: Double) {
        launch {
            interactors.setShipping(fee)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()
}