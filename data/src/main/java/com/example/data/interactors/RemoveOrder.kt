package com.example.data.interactors

import com.example.data.repositories.BillRepository
import com.example.domain.model.BillStatus
import com.example.domain.model.OrderStatus

class RemoveOrder(val billRepository: BillRepository)
{
    suspend operator fun invoke(orderStatus: OrderStatus)=
        billRepository.removeOrder(orderStatus)
}