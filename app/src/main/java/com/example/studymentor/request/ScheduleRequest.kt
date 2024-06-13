package com.example.studymentor.request

data class ScheduleRequest(
    val date: String,
    val time: String,
    val tutorId: Int,
    val studentId: Int
)