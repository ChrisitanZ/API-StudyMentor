package com.example.studymentor.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://restful-api-studymentor.up.railway.app/" // Endpoint del Swagger

    // Lazy initialization para servicios específicos
    val paymentService: PaymentApiService by lazy {
        createService(PaymentApiService::class.java)
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

    val loginApiService: LoginApiService by lazy {
        createService(LoginApiService::class.java)
    }

    // Función privada para crear servicios Retrofit
    private fun <T> createService(serviceClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}