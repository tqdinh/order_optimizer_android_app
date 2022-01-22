package com.example.data.interactors

//import com.example.data..BillRepository
import com.example.domain.model.BillStatus
import com.example.domain.model.OrderStatus
import com.example.data.repositories.BillRepository

class updateOrder(val billRepository: BillRepository) {
    suspend operator fun invoke(order_Status: OrderStatus,new_order_Status: OrderStatus)=
        billRepository.updateOrder(order_Status,new_order_Status)


}