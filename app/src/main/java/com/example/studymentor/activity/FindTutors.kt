package com.example.studymentor.activity

import android.os.Bundle
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

class FindTutors : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tutorAdapter: TutorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_find)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rvTutors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchTutors()

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
                    recyclerView.adapter = tutorAdapter
                } else {
                    Toast.makeText(this@FindTutors, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserTutor>>, t: Throwable) {

                Toast.makeText(this@FindTutors, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}