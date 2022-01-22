package com.example.data.interactors

import com.example.data.repositories.BillRepository
import com.example.domain.model.BillStatus

class GetOrders(val billRepository: BillRepository) {
    suspend operator fun invoke (billStatus: BillStatus)=
        billRepository.getOrders(billStatus)
}