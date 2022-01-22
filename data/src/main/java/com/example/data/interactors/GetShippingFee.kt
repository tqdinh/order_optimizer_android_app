package com.example.data.interactors

import com.example.data.repositories.BillRepository

class GetShippingFee(val billRepository: BillRepository) {
    suspend operator fun invoke()=billRepository.getShippingFee()
}