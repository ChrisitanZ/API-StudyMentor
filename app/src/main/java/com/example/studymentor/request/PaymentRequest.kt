package com.example.studymentor.request

data class PaymentRequest(
    val amount: Double,
    val date: String,
    val studentId: Int,
    val tutorId: Int
)

