package com.example.studymentor.UI

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studymentor.R
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.request.TutorRequestPE
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherRegisterActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)

        val btRegister = findViewById<Button>(R.id.btRegisterTeacher)
        btRegister.setOnClickListener {
            val name = findViewById<EditText>(R.id.etxNameTeacher).text.toString()
            val lastname = findViewById<EditText>(R.id.etxLastnameTeacher).text.toString()
            val email = findViewById<EditText>(R.id.etxEmailTeacher).text.toString()
            val password = findViewById<EditText>(R.id.etxPasswordTeacher).text.toString()
            val cellphone = findViewById<EditText>(R.id.etxCellphoneTeacher).text.toString()
            val specialty = findViewById<EditText>(R.id.etxSpecialtyTeacher).text.toString()
            val image = findViewById<EditText>(R.id.etxImageTeacher).text.toString()

            if (name.isNotEmpty() && lastname.isNotEmpty() && email.isNotEmpty() && password.length >= 8 &&
                cellphone.isNotEmpty() && specialty.isNotEmpty()) {

                val tutorRequest = TutorRequestPE(name, lastname, email, password, cellphone, specialty, image)
                registerTutor(tutorRequest)
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerTutor(tutorRequest: TutorRequestPE) {
        val tutorApiService = RetrofitClient.tutorService

        tutorApiService.createTutor(tutorRequest).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@TeacherRegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Navegar a la actividad de login
                    val intent = Intent(this@TeacherRegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@TeacherRegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@TeacherRegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
