package com.example.studymentor.request

data class ScoreRequest(
    val value: Int,
    val studentId: Int,
    val tutorId: Int
)