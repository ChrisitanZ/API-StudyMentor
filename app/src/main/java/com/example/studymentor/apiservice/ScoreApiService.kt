package com.example.studymentor.apiservice

import com.example.studymentor.model.Score
import com.example.studymentor.request.ScoreRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScoreApiService {
    @GET("api/Score")
    fun getScores(): Call<List<Score>>

    @GET("api/Score/{id}")
    fun getScoresById(@Path("id") id: Int): Call<List<Score>>
    @GET("api/Score/scores/student/{id}")
    fun getScoresByStudentId(@Path("id") id: Int): Call<List<Score>>
    @POST("api/Score")
    fun createScore(@Body score: ScoreRequest): Call<Score>

    @PUT("api/Score/{id}")
    fun updateScore(@Path("id") id: Int, @Body score: ScoreRequest): Call<Score>

    @DELETE("api/Score/{id}")
    fun deleteScore(@Path("id") id: Int): Call<Void>
}