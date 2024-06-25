package com.example.studymentor.UI.Tutor

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
import com.example.studymentor.UI.Student.HomeStudentActivity
import com.example.studymentor.UI.Tutor.StudentListActivity
import com.example.studymentor.UI.Tutor.TutorProfileActivity
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.apiservice.TutorApiService

import com.example.studymentor.model.Tutor
import com.example.studymentor.request.TutorRequestPE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorProfileEditActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var teacherApiService: TutorApiService

    private lateinit var etNameT: EditText
    private lateinit var etLastNameT: EditText
    private lateinit var etEmailT: EditText
    private lateinit var etPhoneT: EditText
    private lateinit var etSpecialtyT: EditText
    private lateinit var etPasswordT: EditText
    private lateinit var etImageURLT: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_edit)

        val btHomeT = findViewById<ImageButton>(R.id.btHomeTE)
        val btStudents = findViewById<ImageButton>(R.id.btStudentsTE)
        val btProfileT = findViewById<ImageButton>(R.id.btProfileTE)
        val btCalendarT = findViewById<ImageButton>(R.id.btCalendarTE)

        btHomeT.setOnClickListener {
            startActivity(Intent(this, HomeStudentActivity::class.java))
        }

        btStudents.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }

        btProfileT.setOnClickListener {
            startActivity(Intent(this, TutorProfileActivity::class.java))
        }

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        teacherApiService = RetrofitClient.tutorService

        etNameT = findViewById(R.id.etNameTE)
        etLastNameT = findViewById(R.id.etLastNameT)
        etEmailT = findViewById(R.id.etEmailT)
        etPhoneT = findViewById(R.id.etPhoneT)
        etSpecialtyT = findViewById(R.id.etSpecialtyT)
        etPasswordT = findViewById(R.id.etPasswordT)
        etImageURLT = findViewById(R.id.etImageURLT)

        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            loadTeacherData(userId)
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
        }

        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfileT)

        btnSaveProfile.setOnClickListener {
            val name = etNameT.text.toString().trim()
            val lastName = etLastNameT.text.toString().trim()
            val email = etEmailT.text.toString().trim()
            val phone = etPhoneT.text.toString().trim()
            val specialty = etSpecialtyT.text.toString().trim()
            val password = etPasswordT.text.toString().trim()
            val imageUrl = etImageURLT.text.toString().trim()

            val teacherRequest = TutorRequestPE(
                name = name.takeIf { it.isNotEmpty() },
                lastname = lastName.takeIf { it.isNotEmpty() },
                email = email.takeIf { it.isNotEmpty() },
                cellphone = phone.takeIf { it.isNotEmpty() },
                specialty = specialty.takeIf { it.isNotEmpty() },
                password = password.takeIf { it.isNotEmpty() },
                image = imageUrl.takeIf { it.isNotEmpty() }
            )

            if (userId != -1) {
                updateTeacherProfile(userId, teacherRequest)
            } else {
                Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTeacherData(userId: Int) {
        teacherApiService.getTutorById(userId).enqueue(object : Callback<Tutor> {
            override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                if (response.isSuccessful) {
                    val teacher = response.body()

                    etNameT.setText(teacher?.name ?: "")
                    etLastNameT.setText(teacher?.lastname ?: "")
                    etEmailT.setText(teacher?.email ?: "")
                    etPhoneT.setText(teacher?.cellphone ?: "")
                    etSpecialtyT.setText(teacher?.specialty ?: "")
                    etPasswordT.setText(teacher?.password ?: "")
                    etImageURLT.setText(teacher?.image ?: "")
                } else {
                    Toast.makeText(this@TutorProfileEditActivity, "Error al cargar datos del profesor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tutor>, t: Throwable) {
                Toast.makeText(this@TutorProfileEditActivity, "Error al cargar datos del profesor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTeacherProfile(userId: Int, teacherRequest: TutorRequestPE) {
        teacherApiService.updateTutor(userId, teacherRequest).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    Toast.makeText(this@TutorProfileEditActivity, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@TutorProfileEditActivity, "Error al actualizar perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@TutorProfileEditActivity, "Error al actualizar perfil: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
