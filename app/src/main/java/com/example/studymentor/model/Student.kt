package com.example.studymentor.model

data class Student(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val birthday: String,
    val cellphone: String,
    val genre: Genre,
    val image: String
)