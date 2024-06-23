package com.example.studymentor.UI.Tutor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.adapter.StudentAdapter
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentListActivity : AppCompatActivity() {

    lateinit var studentAdapter: StudentAdapter
    private lateinit var rvStudents: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btHome = findViewById<ImageButton>(R.id.btHomeT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btPerfil = findViewById<ImageButton>(R.id.btProfileT)
        val btFind = findViewById<Button>(R.id.btSearchS)

        rvStudents = findViewById(R.id.rvStudents)
        rvStudents.layoutManager = LinearLayoutManager(this@StudentListActivity)

        btHome.setOnClickListener {
            val intent = Intent(this@StudentListActivity, HomeTutorActivity::class.java)
            startActivity(intent)
        }

        btPerfil.setOnClickListener {
            val intent = Intent(this@StudentListActivity, TutorProfileActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener {
            val intent = Intent(this@StudentListActivity, TutorCalendarActivity::class.java)
            startActivity(intent)
        }

        btFind.setOnClickListener {
            fetchStudents()
        }
    }

    private fun fetchStudents() {
        val service = RetrofitClient.studentService
        service.getStudents().enqueue(object : Callback<List<Student>> {
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                if (response.isSuccessful) {
                    val students = response.body() ?: emptyList()
                    studentAdapter = StudentAdapter(students)
                    rvStudents.adapter = studentAdapter
                    rvStudents.layoutManager = LinearLayoutManager(this@StudentListActivity)
                } else {
                    Toast.makeText(this@StudentListActivity, "Error al obtener la lista de estudiantes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Toast.makeText(this@StudentListActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
