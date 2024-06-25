package com.example.studymentor.apiservice

import com.example.studymentor.model.Schedule
import com.example.studymentor.request.ScheduleRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScheduleApiService {
    @GET("api/Schedule")
    fun getSchedules(): Call<List<Schedule>>

    @GET("api/Schedule/ByTutor/{id}")
    fun getSchedulesByTutor(@Path("id") tutorId: Int): Call<List<Schedule>>

    @GET("api/Schedule/ByStudent/{id}")
    fun getSchedulesByStudent(@Path("id") tutorId: Int): Call<List<Schedule>>

    @GET("api/Schedule/{id}")
    fun getScheduleById(@Path("id") id: Int): Call<Schedule>

    @POST("api/Schedule")
    fun createSchedule(@Body schedule: ScheduleRequest): Call<Schedule>

    @PUT("api/Schedule/{id}")
    fun updateSchedule(@Path("id") id: Int, @Body schedule: ScheduleRequest): Call<Schedule>

    @DELETE("api/Schedule/{id}")
    fun deleteSchedule(@Path("id") id: Int): Call<Void>
}