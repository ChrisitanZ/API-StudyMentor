package com.example.studymentor.UI.Tutor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R

class HomeTutorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_tutor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btListStudent = findViewById<ImageButton>(R.id.btStudents)

        val btProfile = findViewById<ImageButton>(R.id.btProfileT)


        btListStudent.setOnClickListener {
            val intent = Intent(this@HomeTutorActivity, StudentListActivity::class.java)
            startActivity(intent)
        }

        btProfile.setOnClickListener {
            val intent = Intent(this@HomeTutorActivity, TutorProfileActivity::class.java)
            startActivity(intent)
        }
    }
}