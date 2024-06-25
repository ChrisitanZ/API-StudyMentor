package com.example.studymentor.UI.Tutor

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
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Score
import com.example.studymentor.model.Student
import com.example.studymentor.request.ScoreRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScoreTutorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreAdapter: ScoreAdapter
    private lateinit var spinnerTutors: Spinner
    private var studentList: List<Student> = emptyList()
    private lateinit var sharedPreferences: SharedPreferences
    private var tutorId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_teacher)
        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        tutorId = sharedPreferences.getInt("USER_ID", -1)

        if (tutorId == -1) {
            Toast.makeText(this, "No se pudo obtener el ID del tutor", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si no se encuentra el ID del tutor
            return
        }
        recyclerView = findViewById(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        spinnerTutors = findViewById(R.id.spinnerTutors)
        fetchStudents()

        val fabAddScore: FloatingActionButton = findViewById(R.id.fabAddNote)
        fabAddScore.setOnClickListener {
            showAddScoreDialog()
        }
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
                loadScores(selectedStudentId, tutorId)  // Asumiendo
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
                            scoreAdapter = ScoreAdapter(filteredScores, studentList)
                            recyclerView.adapter = scoreAdapter
                            scoreAdapter.notifyDataSetChanged() // Add this line
                        } else {
                            Toast.makeText(this@ScoreTutorActivity, "No hay puntuaciones para mostrar", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@ScoreTutorActivity, "Error en la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
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

    private fun showAddScoreDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_score, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Agregar Nota")

        val alertDialog = builder.show()

        val edtScoreType = dialogView.findViewById<EditText>(R.id.etNoteType)
        val edtScoreDate = dialogView.findViewById<EditText>(R.id.etNoteDate)
        val edtScoreValue = dialogView.findViewById<EditText>(R.id.etNoteScoreValue)
        val edtScoreStatus = dialogView.findViewById<EditText>(R.id.etNoteStatus)
        val btnAddScore = dialogView.findViewById<Button>(R.id.btnAddNote)

        btnAddScore.setOnClickListener {
            val scoreType = edtScoreType.text.toString()
            val scoreDate = edtScoreDate.text.toString()
            val scoreValue = edtScoreValue.text.toString()
            val scoreStatus = edtScoreStatus.text.toString()

            if (scoreType.isNotEmpty() && scoreDate.isNotEmpty() && scoreValue.isNotEmpty() && scoreStatus.isNotEmpty()) {
                val selectedStudentPosition = spinnerTutors.selectedItemPosition
                val selectedStudentId = studentList[selectedStudentPosition].id
                val scoreRequest = ScoreRequest(
                    type = scoreType,
                    date = scoreDate,
                    scoreValue = scoreValue,
                    status = scoreStatus,
                    studentId = selectedStudentId,
                    tutorId = tutorId  // Asumiendo
                )
                saveScore(scoreRequest)
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveScore(scoreRequest: ScoreRequest) {
        val service = RetrofitClient.scoreService
        service.createScore(scoreRequest).enqueue(object : Callback<Score> {
            override fun onResponse(call: Call<Score>, response: Response<Score>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ScoreTutorActivity, "Nota agregada exitosamente", Toast.LENGTH_SHORT).show()
                    loadScores(scoreRequest.studentId, scoreRequest.tutorId)
                } else {
                    Toast.makeText(this@ScoreTutorActivity, "Error al agregar la nota", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Score>, t: Throwable) {
                Toast.makeText(this@ScoreTutorActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
