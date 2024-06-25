package com.example.studymentor.model

data class Tutor(
    val tutorId: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val cellphone: String,
    val specialty: String,
    val cost: Double,
    val image: String
)