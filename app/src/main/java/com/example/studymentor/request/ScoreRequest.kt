package com.example.studymentor.request

data class ScoreRequest(
    val studentId: Int,
    val tutorId: Int,
    val type: String,
    val date: String,
    val scoreValue:String,
    val status: String,
)