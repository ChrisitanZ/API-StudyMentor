package com.example.studymentor.request

import java.util.Date

data class ScoreRequest(
    val type: String,
    val date: String,
    val scoreValue:String,
    val status: String,
    val studentId: Int,
    val tutorId: Int,
)