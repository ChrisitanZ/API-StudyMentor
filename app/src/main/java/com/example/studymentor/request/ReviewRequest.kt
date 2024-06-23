package com.example.studymentor.request

data class ReviewRequest(
    val textMessagge: String,
    val rating: Int,
    val studentId: Int,
    val date: String,
    val tutorId: Int,
    val type: String
)