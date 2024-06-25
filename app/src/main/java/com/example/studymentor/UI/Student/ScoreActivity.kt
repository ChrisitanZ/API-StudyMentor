package com.example.studymentor.UI.Student

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.adapter.ScoreAdapter
import com.example.studymentor.adapter.StudentScoreAdapter
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Score
import com.example.studymentor.model.Student
import com.example.studymentor.model.Tutor
import com.example.studymentor.request.ScoreRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScoreActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreAdapter: StudentScoreAdapter
    private lateinit var spinnerTutors: Spinner
    private var tutorList: List<Tutor> = emptyList()
    private lateinit var sharedPreferences: SharedPreferences
    private var studentId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_student)
        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        studentId = sharedPreferences.getInt("USER_ID", -1)

        if (studentId == -1) {
            Toast.makeText(this, "No se pudo obtener el ID del estudiante", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si no se encuentra el ID del estudiante
            return
        }
        recyclerView = findViewById(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        spinnerTutors = findViewById(R.id.spinnerTutors)
        fetchStudents()


    }

    private fun fetchStudents() {
        val service = RetrofitClient.tutorService
        service.getTutors().enqueue(object : Callback<List<Tutor>> {
            override fun onResponse(call: Call<List<Tutor>>, response: Response<List<Tutor>>) {
                if (response.isSuccessful) {
                    tutorList = response.body() ?: emptyList()
                    setupSpinner()
                } else {
                    Toast.makeText(this@ScoreActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }


        btHome.setOnClickListener {
            val intent = Intent(this@ScoreActivity, HomeStudentActivity::class.java)
            startActivity(intent)
    }

    private fun fetchStudents() {
        val service = RetrofitClient.tutorService
        service.getTutors().enqueue(object : Callback<List<Tutor>> {
            override fun onResponse(call: Call<List<Tutor>>, response: Response<List<Tutor>>) {
                if (response.isSuccessful) {
                    tutorList = response.body() ?: emptyList()
                    setupSpinner()
                } else {
                    Toast.makeText(this@ScoreActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Tutor>>, t: Throwable) {
                Toast.makeText(this@ScoreActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setupSpinner() {
        val tutorNames = tutorList.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tutorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTutors.adapter = adapter

        spinnerTutors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTutorId = tutorList[position].tutorId
                loadScores(studentId, selectedTutorId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    private fun setupSpinner() {
        val tutorNames = tutorList.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tutorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTutors.adapter = adapter

        spinnerTutors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTutorId = tutorList[position].id
                loadScores(studentId, selectedTutorId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun loadScores(studentId: Int, tutorId: Int) {
        val service = RetrofitClient.scoreService
        service.getScoresByStudentId(studentId).enqueue(object : Callback<List<Score>> {
            override fun onResponse(call: Call<List<Score>>, response: Response<List<Score>>) {
                if (response.isSuccessful) {
                    val scores = response.body()
                    if (scores!= null) {
                        val filteredScores = scores.filter { it.tutorId == tutorId }
                        if (filteredScores.isNotEmpty()) {
                            scoreAdapter = StudentScoreAdapter(filteredScores, tutorList)
                            recyclerView.adapter = scoreAdapter
                            scoreAdapter.notifyDataSetChanged() // Add this line
                        } else {
                            Toast.makeText(this@ScoreActivity, "No hay puntuaciones para mostrar", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@ScoreActivity, "Error en la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ScoreActivity, "Error en la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                Toast.makeText(this@ScoreActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




}
