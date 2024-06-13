package com.example.studymentor.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*object RetrofitClient {

    private const val BASE_URL = ""//endpoint del swaggger

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

    private fun <T> createService(serviceClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}*/