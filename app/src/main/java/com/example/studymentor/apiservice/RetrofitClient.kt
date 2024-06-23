package com.example.studymentor.apiservice

import com.example.studymentor.adapter.DateAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

object RetrofitClient {

    private const val BASE_URL = "https://restful-api-studymentor.up.railway.app/" //endpoint del swaggger

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, DateAdapter())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val paymentService: PaymentApiService by lazy {
        createService(PaymentApiService::class.java)
    }

    val loginApiService: LoginApiService by lazy {
        createService(LoginApiService::class.java)
    }

    val reviewService: ReviewApiService by lazy {
        createService(ReviewApiService::class.java)
    }

    val scheduleService: ScheduleApiService by lazy {
        createService(ScheduleApiService::class.java)
    }

    val tutorService: TutorApiService by lazy {
        createService(TutorApiService::class.java)
    }

    val studentService: StudentApiService by lazy {
        createService(StudentApiService::class.java)
    }

    val scoreService: ScoreApiService by lazy {
        createService(ScoreApiService::class.java)
    }

    private fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }


}