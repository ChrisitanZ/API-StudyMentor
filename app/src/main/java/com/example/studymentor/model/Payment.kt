package com.example.studymentor.model

data class Payment(
    val id: Int,
    val amount: Double,
    val date: String,
    val studentId: Int,
    val tutorId: Int
)