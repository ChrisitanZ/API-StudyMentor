package com.example.studymentor.model

data class Schedule (
    val id: Int,
    val date: String,
    val time: String,
    val tutorId: Int,
    val studentId: Int
)

