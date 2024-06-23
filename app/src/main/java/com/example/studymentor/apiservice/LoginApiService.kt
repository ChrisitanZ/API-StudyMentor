package com.example.studymentor.apiservice

import com.example.studymentor.model.LoginResponse
import com.example.studymentor.model.User
import com.example.studymentor.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
