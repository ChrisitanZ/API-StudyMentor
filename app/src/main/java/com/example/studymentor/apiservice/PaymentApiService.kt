package com.example.studymentor.apiservice

import com.example.studymentor.model.Payment
import com.example.studymentor.request.PaymentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PaymentApiService {

    @GET("api/Payment")
    fun getPayments(): Call<List<Payment>>

    @GET("api/Payment/{id}")
    fun getPaymentById(@Path("id") id: Int): Call<Payment>

    @POST("api/Payment")
    fun createPayment(@Body payment: PaymentRequest): Call<Payment>

    @PUT("api/Payment/{id}")
    fun updatePayment(@Path("id") id: Int, @Body payment: PaymentRequest): Call<Payment>

    @DELETE("api/Payment/{id}")
    fun deletePayment(@Path("id") id: Int): Call<Void>
}