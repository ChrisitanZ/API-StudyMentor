package com.example.studymentor.network


import com.example.studymentor.model.Score
import com.example.studymentor.model.Tutor
import com.example.studymentor.request.ReviewRequest
import com.example.studymentor.request.ScoreRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScoreService {

    @GET("api/Score")
    fun getScores(): Call<List<Score>>
    @GET("api/Score/{id}")
    fun getScoresById(@Path("id") id: Int): Call<List<Score>>
}
