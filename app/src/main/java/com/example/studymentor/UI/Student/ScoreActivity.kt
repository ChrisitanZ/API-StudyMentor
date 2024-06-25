package com.example.studymentor.UI.Student

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R

class ScoreActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btHome = findViewById<ImageButton>(R.id.btHome)

        val btTutorList = findViewById<ImageButton>(R.id.btTutors)

        val btPerfil = findViewById<ImageButton>(R.id.btPerfilEstudiante)


<<<<<<< Updated upstream
        btHome.setOnClickListener {
            val intent = Intent(this@ScoreActivity, HomeStudentActivity::class.java)
            startActivity(intent)
=======
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
>>>>>>> Stashed changes
        }

        btTutorList.setOnClickListener {
            val intent = Intent(this@ScoreActivity, TutorListActivity::class.java)
            startActivity(intent)
        }

        btPerfil.setOnClickListener{
            val intent = Intent(this@ScoreActivity, StudentProfileActivity::class.java)
            startActivity(intent)
        }

    }
}