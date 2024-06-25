package com.example.studymentor.request

data class ScheduleRequest(
    val studentId: Int,
    val isAvailable: Boolean,
)