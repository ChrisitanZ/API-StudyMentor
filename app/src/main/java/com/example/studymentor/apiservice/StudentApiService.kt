package com.example.studymentor.apiservice
import com.example.studymentor.model.Student
import com.example.studymentor.request.StudentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface StudentApiService {

    @GET("api/Student")
    fun getStudents(): Call<List<Student>>

    @GET("api/Student/{id}")
    fun getStudentById(@Path("id") id: Int): Call<Student>

    @POST("api/Student")
    fun createStudent(@Body student: StudentRequest): Call<Student>

    @PUT("api/Student/{id}")
    fun updateStudent(@Path("id") id: Int, @Body student: StudentRequest): Call<Student>

    @DELETE("api/Student/{id}")
    fun deleteStudent(@Path("id") id: Int): Call<Void>
}