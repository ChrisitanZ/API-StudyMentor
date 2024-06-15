package com.example.studymentor.model

import java.util.Date

data class Review(
    val id: Int,
    val textMessage: String,
    val rating: Int,
    val studentId: Int,
    val tutorId: Int,
    val date: Date,
)