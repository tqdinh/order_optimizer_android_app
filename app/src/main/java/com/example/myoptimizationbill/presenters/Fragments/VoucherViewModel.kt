package com.example.myoptimizationbill.presenters.Fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Voucher

import com.example.domain.model.VoucherStatus
import com.example.myoptimizationbill.framework.Interactors
import com.example.myoptimizationbill.framework.MyViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class VoucherViewModel(application: Application, interactors: Interactors) :
    MyViewModel(application, interactors), CoroutineScope {

    val vouchersLiveData: MutableLiveData<ArrayList<VoucherStatus>> = MutableLiveData()
    val vouchers: ArrayList<VoucherStatus> = ArrayList()


    fun addVoucher(voucherStatus: VoucherStatus) {
        vouchers.add(voucherStatus)
        vouchersLiveData.postValue(vouchers)
        GlobalScope.launch {
            interactors.storeVouchers(voucherStatus)

        }

    }

    fun removeVoucher(voucherStatus: VoucherStatus) {
        vouchers.remove(voucherStatus)
        vouchersLiveData.postValue(vouchers)
        launch {
            interactors.removeVoucher(voucherStatus)
        }
    }

    fun updateVoucher(oldVoucher: VoucherStatus, newVoucher: VoucherStatus) {
        var index_of_voucher = vouchers.indexOf(oldVoucher)
        vouchers.remove(oldVoucher)
        vouchers.add(index_of_voucher, newVoucher)
        Log.d("FRAGMENT_", "index= " + index_of_voucher + "update new size= " + vouchers.size)
        vouchersLiveData.postValue(vouchers)
        GlobalScope.launch {
           interactors.updateVoucher(oldVoucher, newVoucher)
        }


    }


    fun test() {
        addVoucher(
            VoucherStatus(
                _apply_from = 0,
                _percentage_of_discount = 0.001,
                _maximum_quantity_apply = 0.0
            )
        )
        addVoucher(
            VoucherStatus(
                _apply_from = 100*1000,
                _percentage_of_discount = 100.0,
                _maximum_quantity_apply = 20*1000.0
            )
        )
        addVoucher(
            VoucherStatus(
                _apply_from = 80*1000,
                _percentage_of_discount = 100.0,
                _maximum_quantity_apply = 18*1000.0
            )
        )

        addVoucher(
            VoucherStatus(
                _apply_from = 120*1000,
                _percentage_of_discount =100.0,
                _maximum_quantity_apply = 25*1000.0
            )
        )

        addVoucher(
            VoucherStatus(
                _apply_from = 150*1000,
                _percentage_of_discount =100.0,
                _maximum_quantity_apply = 35*1000.0
            )
        )

        addVoucher(
            VoucherStatus(
                _apply_from = 200*1000,
                _percentage_of_discount =100.0,
                _maximum_quantity_apply = 70*1000.0
            )
        )

//        addVoucher(
//            VoucherStatus(
//                _apply_from = 200*1000,
//                _percentage_of_discount =100.0,
//                _maximum_quantity_apply = 40*1000.0
//            )
//        )


//        addVoucher(
//            VoucherStatus(
//                _apply_from = 100,
//                _percentage_of_discount = 0.45,
//                _maximum_quantity_apply = 50.0
//            )
//        )
//        addVoucher(
//            VoucherStatus(
//                _apply_from = 200,
//                _percentage_of_discount = 0.4,
//                _maximum_quantity_apply = 80.0
//            )
//        )
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

}