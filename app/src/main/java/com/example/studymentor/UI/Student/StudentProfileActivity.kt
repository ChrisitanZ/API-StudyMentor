package com.example.studymentor.UI.Student

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

class StudentProfileActivity: AppCompatActivity(){
    private lateinit var tvNameS: TextView
    private lateinit var tvBirthS: TextView
    private lateinit var tvEmailS: TextView
    private lateinit var tvCellphoneS: TextView
    private lateinit var imageView5: ImageView
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
        val studentId = 26 //Reemplazar por el adecuado

        val service = RetrofitClient.studentService
        service.getStudentById(studentId).enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful) {
                    val student = response.body()
                    if (student != null){
                        tvNameS.text = "Nombre Completo: ${student.name} ${student.lastname}"
                        tvBirthS.text = "Fecha de Nacimiento: ${student.birthday}"
                        tvEmailS.text = "Email: ${student.email}"
                        tvCellphoneS.text = "Telefono: ${student.cellphone}"

                        Picasso.get()
                            .load(student.image)
                            .into(imageView5)
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
}