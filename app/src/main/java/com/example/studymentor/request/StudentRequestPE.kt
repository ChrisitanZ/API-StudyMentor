package com.example.studymentor.request



data class StudentRequestPE(
    val name: String?,
    val lastName: String?,
    val email: String?,
    val cellphone: String?,
    val genre: GenreRequest?,     // Género es opcional si no se actualiza
    val birthday: String?, // Cumpleaños es opcional si no se actualiza
    val password: String?, // Contraseña es opcional si no se actualiza
    val image: String?,
)