package com.example.studymentor.request

data class TutorRequest(
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val cellphone: String,
    val specialty: String,
    val cost: Double,
    val image: String
)