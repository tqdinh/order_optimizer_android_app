package com.example.data.interactors

import com.example.data.repositories.BillRepository

class SetShippingFee(val billRepository: BillRepository) {
    suspend operator fun invoke(fee:Double)=
        billRepository.setShippingFee(fee)
}