package com.example.studymentor.apiservice

import com.example.studymentor.model.Review
import com.example.studymentor.model.ReviewResponse
import com.example.studymentor.request.ReviewRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewApiService {
    @GET("api/Review")
    fun getReviews(): Call<List<Review>>

    @GET("api/Review/{id}")
    fun getReviewById(@Path("id") id: Int): Call<Review>

    @POST("api/Review")
    fun createReview(@Body review: ReviewRequest): Call<ReviewResponse>

    @PUT("api/Review/{id}")
    fun updateReview(@Path("id") id: Int, @Body review: ReviewRequest): Call<ReviewResponse>

    @DELETE("api/Review/{id}")
    fun deleteReview(@Path("id") id: Int): Call<Void>
}