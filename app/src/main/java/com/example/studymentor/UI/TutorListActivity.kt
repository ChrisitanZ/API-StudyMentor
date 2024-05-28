package com.example.studymentor.UI

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

import com.example.studymentor.adapter.TutorAdapter
import com.example.studymentor.model.UserTutor
import com.example.studymentor.network.TutorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        rvTutors = findViewById(R.id.rvTutors)

        btHome.setOnClickListener {
            val intent = Intent(this@TutorListActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btFind.setOnClickListener{
            fetchTutors()
        }

    }

    private fun fetchTutors() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-vlxw.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TutorService::class.java)
        service.getTutors().enqueue(object : Callback<List<UserTutor>> {
            override fun onResponse(call: Call<List<UserTutor>>, response: Response<List<UserTutor>>) {
                if (response.isSuccessful) {
                    val tutors = response.body() ?: emptyList()
                    tutorAdapter = TutorAdapter(tutors)
                    rvTutors.adapter = tutorAdapter
                    rvTutors.layoutManager = LinearLayoutManager(this@TutorListActivity)
                } else {
                    Toast.makeText(this@TutorListActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserTutor>>, t: Throwable) {
                Toast.makeText(this@TutorListActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}