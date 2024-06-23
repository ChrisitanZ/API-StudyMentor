package com.example.studymentor.UI.Tutor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R

class TutorProfileEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_profile_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btHome = findViewById<ImageButton>(R.id.btHomeT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btListStudent = findViewById<ImageButton>(R.id.btStudents)
        val btProfile = findViewById<ImageButton>(R.id.btProfileT)


        btHome.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, HomeTutorActivity::class.java)
            startActivity(intent)
        }

        btListStudent.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, StudentListActivity::class.java)
            startActivity(intent)
        }

        btProfile.setOnClickListener {
            val intent = Intent(this@TutorProfileEditActivity, TutorProfileActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener{
            val intent = Intent(this@TutorProfileEditActivity, TutorCalendarActivity::class.java)
            startActivity(intent)
        }
    }
}