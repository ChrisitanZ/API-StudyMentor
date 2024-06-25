package com.example.studymentor.apiservice

import com.example.studymentor.model.Tutor
import com.example.studymentor.request.TutorRequestPE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TutorApiService {
    @GET("api/Tutor")
    fun getTutors(): Call<List<Tutor>>

    @GET("api/Tutor/{id}")
    fun getTutorById(@Path("id") id: Int): Call<Tutor>

    @POST("api/Tutor")
    fun createTutor(@Body tutor: TutorRequestPE): Call<ResponseBody>

    @PUT("api/Tutor/{id}")
    fun updateTutor(@Path("id") id: Int, @Body tutorRequest: TutorRequestPE): Call<Boolean>

    @DELETE("api/Tutor/{id}")
    fun deleteTutor(@Path("id") id: Int): Call<Void>
}