package com.example.studymentor.UI.Tutor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R

class TutorsReviewsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutors_reviews_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btHomeT = findViewById<ImageButton>(R.id.btHomeT)
        val btProfileT = findViewById<ImageButton>(R.id.btProfileT)

        val btListStudent = findViewById<ImageButton>(R.id.btStudents)


        btHomeT.setOnClickListener {
            val intent = Intent(this@TutorsReviewsListActivity, HomeTutorActivity::class.java)
            startActivity(intent)
        }

        btProfileT.setOnClickListener {
            val intent = Intent(this@TutorsReviewsListActivity, TutorProfileActivity::class.java)
            startActivity(intent)
        }

        btListStudent.setOnClickListener {
            val intent = Intent(this@TutorsReviewsListActivity, StudentListActivity::class.java)
            startActivity(intent)
        }

    }
}