package com.example.studymentor.model

import java.util.Date

data class Score(
    val id: Int,
    val studentId: Int,
    val tutorId: Int,
    val type: String,
    val date: Date,
    val scoreValue:String,
    val status: String,
)
