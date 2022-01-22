package com.example.data.interactors

//import com.example.data..BillRepository
import com.example.domain.model.BillStatus
import com.example.domain.model.OrderStatus
import com.example.data.repositories.BillRepository
import com.example.domain.model.OptimizeRequest
import com.example.domain.model.VoucherStatus
import org.json.JSONObject

class RequestCalculate(val billRepository: BillRepository) {
    suspend operator fun invoke(request: OptimizeRequest)=
        billRepository.requestCalculate(request)

}