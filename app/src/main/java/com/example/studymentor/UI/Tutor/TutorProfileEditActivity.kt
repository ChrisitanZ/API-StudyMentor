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
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.apiservice.TutorApiService
import com.example.studymentor.model.Tutor
import com.example.studymentor.request.TutorRequestPE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorProfileEditActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tutorApiService: TutorApiService

    private lateinit var etNameP: EditText
    private lateinit var etLastNameP: EditText
    private lateinit var etEmailP: EditText
    private lateinit var etPhoneP: EditText
    private lateinit var etSpecialty: EditText
    private lateinit var etCost: EditText
    private lateinit var etPassword: EditText
    private lateinit var etImageURL: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_profile_edit)

        val btHome = findViewById<ImageButton>(R.id.btHomeT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btListStudent = findViewById<ImageButton>(R.id.btStudents)
        val btProfile = findViewById<ImageButton>(R.id.btProfileT)

        btHome.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, HomeTutorActivity::class.java)
            startActivity(intent)
        }

        btListStudent.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, StudentListActivity::class.java)
            startActivity(intent)
        }

        btProfile.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, TutorProfileActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, TutorCalendarActivity::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        tutorApiService = RetrofitClient.tutorService

        etNameP = findViewById(R.id.etNamePT)
        etLastNameP = findViewById(R.id.etLastNamePT)
        etEmailP = findViewById(R.id.etEmailPT)
        etPhoneP = findViewById(R.id.etPhonePET)
        etSpecialty = findViewById(R.id.etSpecialtyPET)
        etPassword = findViewById(R.id.etPasswordPET)
        etImageURL = findViewById(R.id.etImageURLPET)

        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            loadTutorData(userId)
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
        }

        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfilePET)
        btnSaveProfile.setOnClickListener {
            val name = etNameP.text.toString().trim()
            val lastName = etLastNameP.text.toString().trim()
            val email = etEmailP.text.toString().trim()
            val phone = etPhoneP.text.toString().trim()
            val specialty = etSpecialty.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val imageUrl = etImageURL.text.toString().trim()

            val tutorRequest = TutorRequestPE(
                name = name.takeIf { it.isNotEmpty() },
                lastname = lastName.takeIf { it.isNotEmpty() },
                email = email.takeIf { it.isNotEmpty() },
                password = password.takeIf { it.isNotEmpty() },
                cellphone = phone.takeIf { it.isNotEmpty() },
                specialty = specialty.takeIf { it.isNotEmpty() },
                image = imageUrl.takeIf { it.isNotEmpty() }
            )

            if (userId != -1) {
                updateTutorProfile(userId, tutorRequest)
            } else {
                Toast.makeText(this, "Error: No se pudo obtener el ID de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTutorData(userId: Int) {
        tutorApiService.getTutorById(userId).enqueue(object : Callback<Tutor> {
            override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                if (response.isSuccessful) {
                    val tutor = response.body()

                    etNameP.setText(tutor?.name ?: "")
                    etLastNameP.setText(tutor?.lastname ?: "")
                    etEmailP.setText(tutor?.email ?: "")
                    etPhoneP.setText(tutor?.cellphone ?: "")
                    etSpecialty.setText(tutor?.specialty ?: "")
                    etPassword.setText(tutor?.password ?: "")
                } else {
                    Toast.makeText(this@TutorProfileEditActivity, "Error al cargar datos del tutor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tutor>, t: Throwable) {
                Toast.makeText(this@TutorProfileEditActivity, "Error al cargar datos del tutor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTutorProfile(userId: Int, tutorRequest: TutorRequestPE) {
        tutorApiService.updateTutor(userId, tutorRequest).enqueue(object : Callback<Boolean> {
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
