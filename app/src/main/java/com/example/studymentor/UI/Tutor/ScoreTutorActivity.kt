package com.example.studymentor.UI.Tutor

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
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Score
import com.example.studymentor.model.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScoreTutorActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreAdapter: ScoreAdapter
    private lateinit var spinnerTutors: Spinner
    private var studentList: List<Student> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_teacher)

        recyclerView = findViewById(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        spinnerTutors = findViewById(R.id.spinnerTutors)
        fetchStudents()
    }

    private fun fetchStudents() {
        val service = RetrofitClient.studentService
        service.getStudents().enqueue(object : Callback<List<Student>> {
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                if (response.isSuccessful) {
                    studentList = response.body() ?: emptyList()
                    setupSpinner()
                } else {
                    Toast.makeText(this@ScoreTutorActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Toast.makeText(this@ScoreTutorActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner() {
        val studentNames = studentList.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, studentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTutors.adapter = adapter

        spinnerTutors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedStudentId = studentList[position].id
                loadScores(2, selectedStudentId)
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
                    if (scores != null) {
                        scoreAdapter = ScoreAdapter(scores)
                        recyclerView.adapter = scoreAdapter
                    } else {
                        Toast.makeText(this@ScoreTutorActivity, "Lista de puntuaciones nula", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ScoreTutorActivity, "Error en la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                Toast.makeText(this@ScoreTutorActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}