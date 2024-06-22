package com.example.studymentor.network

import com.example.studymentor.model.Review
import com.example.studymentor.model.ReviewResponse
import com.example.studymentor.request.ReviewRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewService {

    @POST("api/Review")
    fun createReview(@Body review: ReviewRequest): Call<ReviewResponse>
}
