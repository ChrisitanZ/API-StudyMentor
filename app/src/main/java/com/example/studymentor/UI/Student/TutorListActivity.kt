package com.example.studymentor.UI.Student

import android.content.Intent
import android.os.Bundle
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
import com.example.studymentor.StudentCalendarActivity

import com.example.studymentor.adapter.TutorAdapter
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Tutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorListActivity : AppCompatActivity() {

    lateinit var tutorAdapter: TutorAdapter

    private lateinit var rvTutors: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val btHome = findViewById<ImageButton>(R.id.btHome)
        val btFind = findViewById<Button>(R.id.btSearch)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendar)
        val btPerfil = findViewById<ImageButton>(R.id.btPerfilEstudiante)

        rvTutors = findViewById(R.id.rvTutors)

        btHome.setOnClickListener {
            val intent = Intent(this@TutorListActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }


        btPerfil.setOnClickListener {
            val intent = Intent(this@TutorListActivity, StudentProfileActivity::class.java)
            startActivity(intent)
        }

        btFind.setOnClickListener{
            fetchTutors()
        }

        btCalendar.setOnClickListener{
            val intent = Intent(this@TutorListActivity, StudentCalendarActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fetchTutors() {
        val service = RetrofitClient.tutorService
        service.getTutors().enqueue(object : Callback<List<Tutor>> {
            override fun onResponse(call: Call<List<Tutor>>, response: Response<List<Tutor>>) {
                if (response.isSuccessful) {
                    val tutors = response.body() ?: emptyList()
                    tutorAdapter = TutorAdapter(tutors)
                    rvTutors.adapter = tutorAdapter
                    rvTutors.layoutManager = LinearLayoutManager(this@TutorListActivity)
                } else {
                    Toast.makeText(this@TutorListActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Tutor>>, t: Throwable) {
                Toast.makeText(this@TutorListActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

