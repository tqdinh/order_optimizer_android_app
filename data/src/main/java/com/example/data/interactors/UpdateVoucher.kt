package com.example.data.interactors

import com.example.data.repositories.BillRepository
import com.example.domain.model.BillStatus
import com.example.domain.model.VoucherStatus

class UpdateVoucher(val billRepository: BillRepository) {
    suspend operator fun invoke(voucher_Status: VoucherStatus,new_voucher_Status: VoucherStatus)=
        billRepository.updateVoucher(voucher_Status,new_voucher_Status)
}