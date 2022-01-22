package com.example.data.remote

import com.example.domain.model.OptimizeRequest
import com.example.domain.model.OptimizedRespone
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CalculateOptimize
{
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("http://192.168.3.101:5000/dishes")
    fun makeOptimizeRequest(@Body request: OptimizeRequest): Single<OptimizedRespone>

}