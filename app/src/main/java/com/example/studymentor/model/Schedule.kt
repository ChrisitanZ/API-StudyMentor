package com.example.studymentor.model

data class Schedule(
    val id: Int,
    val day: String,
    val tutorHours: Int,
    val startingHour: String,
    val price: Double,
    val isAvailable: Boolean,
    val tutorId: Int,
    val studentId: Int
)

