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
import com.example.studymentor.UI.Student.HomeStudentActivity
import com.example.studymentor.UI.Tutor.HomeTutorActivity
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.LoginResponse
import com.example.studymentor.request.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var userType: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userType = intent.getStringExtra("userType")
        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)

        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegisterLogin = findViewById<Button>(R.id.btRegisterLogin)

        btLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.etxEmailLogin).text.toString()
            val password = findViewById<EditText>(R.id.etxPasswordLogin).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)
                loginUser(loginRequest)
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        btRegisterLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        val loginApiService = RetrofitClient.loginApiService

        loginApiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    // Guardar el tipo de usuario y el ID correspondiente en SharedPreferences
                    when (userType) {
                        "student" -> saveStudentSession(loginResponse.id)
                        "teacher" -> saveTeacherSession(loginResponse.id)
                    }

                    // Navegar a la actividad correspondiente según el tipo de usuario
                    navigateToHomeActivity(userType!!, loginResponse.token)
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveStudentSession(studentId: Int) {
        sharedPreferences.edit().apply {
            putInt("USER_ID", studentId)
            putString("USER_TYPE", "student")
            remove("TEACHER_ID") // Limpiar el TEACHER_ID si existe
            apply()
        }
    }

    private fun saveTeacherSession(teacherId: Int) {
        sharedPreferences.edit().apply {
            putInt("USER_ID", teacherId)
            putString("USER_TYPE", "teacher")
            remove("STUDENT_ID") // Limpiar el STUDENT_ID si existe
            apply()
        }
    }

    private fun navigateToHomeActivity(userType: String, token: String) {
        val intent = when (userType) {
            "student" -> Intent(this@LoginActivity, HomeStudentActivity::class.java)
            "teacher" -> Intent(this@LoginActivity, HomeTutorActivity::class.java)
            else -> null
        }
        intent?.putExtra("TOKEN", token)
        startActivity(intent)
        finish()
    }
}
