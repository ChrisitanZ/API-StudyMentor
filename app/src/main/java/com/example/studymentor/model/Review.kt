package com.example.studymentor.model

data class Review(
    val id: Int,
    val content: String,
    val rating: Int,
    val studentId: Int,
    val tutorId: Int
)