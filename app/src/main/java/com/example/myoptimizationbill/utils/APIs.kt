package com.example.data.utils

import com.example.domain.model.OptimizeRequest
import com.example.domain.model.OptimizedRespone
import com.example.myoptimizationbill.utils.Constants
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface APIs
{
    //@Headers("X-Requested-With:XMLHttpRequest")
    @Headers("Content-Type: application/json")
    @POST("/dishes")
    fun requestCalculate(@Body request: OptimizeRequest): Call<OptimizedRespone>
    @GET("/locations")
    fun postLoc():Call<JsonObject>

    @GET("api/users")
    fun sampleGet(): Call<JsonObject>

    companion object {

        //var BASE_URL = "https://reqres.in/"
        var BASE_URL = Constants.serverDomain

        fun create(): APIs {


            val okHttpClientBuilder = OkHttpClient().newBuilder()
            okHttpClientBuilder.connectTimeout(300, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(300, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(300, TimeUnit.SECONDS)


            val okHttpClient = okHttpClientBuilder.build()

           var gson=GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(APIs::class.java)

        }
    }
}