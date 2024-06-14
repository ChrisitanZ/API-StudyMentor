package com.example.studymentor.request

data class StudentRequest(
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val birthday: String,
    val cellphone: String,
    val genre: GenreRequest,
    val image: String
)