package com.example.studymentor.model

data class Student(
    val id: Int,
    val name: String,
    val lastname: String,
    val password: String,
    val email: String,
    val birthday: String,
    val cellphone: String,
    val image: String,
    val genre: Genre
)
