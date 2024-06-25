package com.example.studymentor.UI.Student

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.StudentCalendarActivity
import com.example.studymentor.UI.MainActivity
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Student
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class StudentProfileActivity: AppCompatActivity(){
    private lateinit var tvNameS: TextView
    private lateinit var tvBirthS: TextView
    private lateinit var tvEmailS: TextView
    private lateinit var tvCellphoneS: TextView
    private lateinit var imageView5: ImageView

    private lateinit var sharedPreferences: SharedPreferences
    private var studentId: Int = -1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        studentId = sharedPreferences.getInt("USER_ID", -1)

        if (studentId == -1) {
            Toast.makeText(this, "No se pudo obtener el ID del estudiante", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si no se encuentra el ID del estudiante
            return
        }

        val btHome = findViewById<ImageButton>(R.id.btHome)
        val btTutorList = findViewById<ImageButton>(R.id.btTutors)
        val btEditS = findViewById<Button>(R.id.btEditS)
        val btReviewsS = findViewById<Button>(R.id.btReviewsS)
        val btExitS = findViewById<Button>(R.id.btExitS)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendar)

        tvNameS = findViewById(R.id.tvNameS)
        tvBirthS = findViewById(R.id.tvBirthS)
        tvEmailS = findViewById(R.id.tvEmailS)
        tvCellphoneS = findViewById(R.id.tvCellphoneS)
        imageView5 = findViewById(R.id.imageView5)


        btHome.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btTutorList.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, TutorListActivity::class.java)
            startActivity(intent)
        }

        btEditS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, StudentProfileEditActivity::class.java)
            startActivity(intent)
        }

        btReviewsS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, StudentsReviewsListActivity::class.java)
            startActivity(intent)
        }

        btExitS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener{
            val intent = Intent(this@StudentProfileActivity, StudentCalendarActivity::class.java)
            startActivity(intent)
        }

        fetchInfoStudent()
    }

    private fun fetchInfoStudent() {
        val service = RetrofitClient.studentService
        service.getStudentById(studentId).enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful) {
                    val student = response.body()
                    if (student != null){
                        tvNameS.text = "Nombre Completo: ${student.name} ${student.lastname}"
                        tvBirthS.text = "Fecha de Nacimiento: ${calculateAge(student.birthday)}"
                        tvEmailS.text = "Email: ${student.email}"
                        tvCellphoneS.text = "Telefono: ${student.cellphone}"

                        if (!student.image.isNullOrEmpty()) {
                            Picasso.get()
                                .load(student.image)
                                .into(imageView5, object : com.squareup.picasso.Callback {
                                    override fun onSuccess() {
                                        // Imagen cargada exitosamente
                                    }

                                    override fun onError(e: java.lang.Exception?) {
                                        // Error al cargar la imagen, puedes ocultar el ImageView
                                        imageView5.visibility = View.GONE
                                    }
                                })
                        } else {
                            imageView5.visibility = View.GONE
                        }
                    }
                } else {
                    Toast.makeText(this@StudentProfileActivity, "Error al obtener el estudiante", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<Student>, t: Throwable) {
                Toast.makeText(this@StudentProfileActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun calculateAge(birthday: String): String {
        val birthDate = LocalDate.parse(birthday.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
        val currentDate = LocalDate.now()
        val age = ChronoUnit.YEARS.between(birthDate, currentDate)
        return "$age years"
    }
}