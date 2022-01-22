package com.example.data.interactors

//import com.example.data..BillRepository
import com.example.domain.model.BillStatus
import com.example.domain.model.OrderStatus
import com.example.data.repositories.BillRepository
import com.example.domain.model.VoucherStatus

class StoreVouchers(val billRepository: BillRepository) {
    suspend operator fun invoke(voucherStatus: VoucherStatus)=
        billRepository.storeVouchers(voucherStatus)
}