package com.example.studymentor.model

data class Student(
    val id:Int,
    val name: String,
    val lastName: String,
    val email: String,
    val cellphone: String,
    val genre: Genre?,     // Género es opcional si no se actualiza
    val birthday: String, // Cumpleaños es opcional si no se actualiza
    val password: String?, // Contraseña es opcional si no se actualiza
    val image: String?     // Imagen es opcional si no se actualiza
)

