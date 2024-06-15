package com.example.studymentor.request

data class ReviewRequest(
    val content: String,
    val rating: Int,
    val studentId: Int,
    val tutorId: Int
)