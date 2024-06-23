package com.example.studymentor.UI


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studymentor.R
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.request.GenreRequest
import com.example.studymentor.request.StudentRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextDateOfBirth: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth)
        editTextDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }

        val btRegister = findViewById<Button>(R.id.btRegister)
        btRegister.setOnClickListener {
            registerStudent()
        }
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val dateString = dateFormat.format(selectedDate.time)

                editTextDateOfBirth.setText(dateString)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun registerStudent() {
        val studentService = RetrofitClient.studentService

        val editTextName = findViewById<EditText>(R.id.etNameP)
        val editTextLastName = findViewById<EditText>(R.id.etLastNameP)
        val editTextEmail = findViewById<EditText>(R.id.etxEmail)
        val editTextPassword = findViewById<EditText>(R.id.etxPassword)
        val editTextCellphone = findViewById<EditText>(R.id.editTextPhone)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

        val name = editTextName.text.toString()
        val lastName = editTextLastName.text.toString()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        val birthday = editTextDateOfBirth.text.toString()
        val cellphone = editTextCellphone.text.toString()
        val selectedGenderId = genderRadioGroup.checkedRadioButtonId
        val (selectedGender, genreCode) = when (selectedGenderId) {
            R.id.maleRadioButton -> "Masculino" to "M"
            R.id.femaleRadioButton -> "Femenino" to "F"
            else -> "" to ""
        }

        val studentRequest = StudentRequest(
            name = name,
            lastname = lastName,
            email = email,
            password = password,
            birthday = birthday, // Asegúrate de pasar la fecha correcta si es requerida
            cellphone = cellphone,
            genre = GenreRequest(nameGenre = selectedGender, code = genreCode),
            image = ""
        )

        studentService.createStudent(studentRequest).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val success = responseBody?.toBoolean() ?: false

                    if (success) {
                        Toast.makeText(this@RegisterActivity, "Estudiante registrado correctamente", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Error al registrar estudiante", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API_ERROR", "Error al registrar estudiante: ${response.code()}")
                    Toast.makeText(this@RegisterActivity, "Error al registrar estudiante", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("NETWORK_ERROR", "Error de red: ${t.message}", t)
                Toast.makeText(this@RegisterActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
