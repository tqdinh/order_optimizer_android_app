package com.example.data.interactors

import com.example.data.repositories.BillRepository
import com.example.domain.model.BillStatus

class GetVouchers(val billRepository: BillRepository) {
    suspend operator fun  invoke(billStatus: BillStatus)=
        billRepository.getVouchers(billStatus)
}