package com.example.studymentor.apiservice

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("login") // Replace with your actual endpoint
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Any>
}
