package com.example.studymentor.UI.Student

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studymentor.R
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.apiservice.StudentApiService

import com.example.studymentor.model.Student
import com.example.studymentor.request.GenreRequest
import com.example.studymentor.request.StudentRequestPE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StudentProfileEditActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var studentApiService: StudentApiService

    private lateinit var etNameP: EditText
    private lateinit var etLastNameP: EditText
    private lateinit var etEmailP: EditText
    private lateinit var etPhoneP: EditText
    private lateinit var etGenreName: EditText
    private lateinit var etCode: EditText
    private lateinit var etPassword: EditText
    private lateinit var etBirthday: EditText
    private lateinit var etImageURL: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile_edit)



        val btHomeT = findViewById<ImageButton>(R.id.btHomeT)
        val btStudents = findViewById<ImageButton>(R.id.btStudents)
        val btProfileT = findViewById<ImageButton>(R.id.btProfileT)
        val btCalendarT = findViewById<ImageButton>(R.id.btCalendarT)

        btHomeT.setOnClickListener {
            startActivity(Intent(this, HomeStudentActivity::class.java))
        }


        btStudents.setOnClickListener {
            // Redirigir a la pantalla de estudiantes (StudentsActivity)
            startActivity(Intent(this, TutorListActivity::class.java))
        }

        btProfileT.setOnClickListener {
            // Redirigir a la pantalla de perfil (ProfileActivity)
            startActivity(Intent(this, StudentProfileActivity::class.java))
        }
        

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        studentApiService = RetrofitClient.studentService

        etNameP = findViewById(R.id.etNameP)
        etLastNameP = findViewById(R.id.etLastNameP)
        etEmailP = findViewById(R.id.etEmailP)
        etPhoneP = findViewById(R.id.etPhoneP)
        etGenreName = findViewById(R.id.etGenre)
        etCode = findViewById(R.id.etCode)
        etPassword = findViewById(R.id.etPassword)
        etBirthday = findViewById(R.id.etBirthday)
        etImageURL = findViewById(R.id.etImageURL)

        // Obtener el ID de usuario almacenado en SharedPreferences
        val userId = sharedPreferences.getInt("USER_ID", -1)

        // Cargar los datos actuales del estudiante utilizando userId
        if (userId != -1) {
            loadStudentData(userId)
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
        }


        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)

        btnSaveProfile.setOnClickListener {
            val name = etNameP.text.toString().trim()
            val lastName = etLastNameP.text.toString().trim()
            val email = etEmailP.text.toString().trim()
            val phone = etPhoneP.text.toString().trim()
            val genreName = etGenreName.text.toString().trim()
            val genreCode = etCode.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val birthday = etBirthday.text.toString().trim()
            val imageUrl = etImageURL.text.toString().trim()  // Obtener la URL de la imagen desde el EditText

            // Crear instancia de StudentRequestPE con los valores actuales para los campos no modificados
            val studentRequest = StudentRequestPE(
                name = name.takeIf { it.isNotEmpty() },
                lastName = lastName.takeIf { it.isNotEmpty() },
                email = email.takeIf { it.isNotEmpty() },
                cellphone = phone.takeIf { it.isNotEmpty() },
                genre = GenreRequest(genreName, genreCode).takeIf { genreName.isNotEmpty() && genreCode.isNotEmpty() },
                birthday = birthday.takeIf { it.isNotEmpty() },
                password = password.takeIf { it.isNotEmpty() },
                image = imageUrl.takeIf { it.isNotEmpty() }
            )

            // Verificar que userId esté definido y no sea -1 (valor por defecto)
            if (userId != -1) {
                // Llamar a la función para actualizar el perfil
                updateStudentProfile(userId, studentRequest)
            } else {
                Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadStudentData(userId: Int) {
        studentApiService.getStudentById(userId).enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful) {
                    val student = response.body()

                    // Llenar los EditText con los datos del estudiante
                    etNameP.setText(student?.name ?: "")
                    etLastNameP.setText(student?.lastname ?: "")
                    etEmailP.setText(student?.email ?: "")
                    etPhoneP.setText(student?.cellphone ?: "")
                    etGenreName.setText(student?.genre?.nameGenre ?: "")
                    etCode.setText(student?.genre?.code ?: "")
                    etPassword.setText(student?.password ?: "")
                    etBirthday.setText(student?.birthday ?: "")
                    // Aquí puedes llenar otros campos como género, fecha de nacimiento, etc., si es necesario
                } else {
                    Toast.makeText(this@StudentProfileEditActivity, "Error al cargar datos del estudiante", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(this@StudentProfileEditActivity, "Error al cargar datos del estudiante: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateStudentProfile(userId: Int, studentRequest: StudentRequestPE) {
        studentApiService.updateStudent(userId, studentRequest).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    Toast.makeText(this@StudentProfileEditActivity, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@StudentProfileEditActivity, "Error al actualizar perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@StudentProfileEditActivity, "Error al actualizar perfil: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
