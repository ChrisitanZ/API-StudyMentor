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
import com.example.studymentor.UI.MainActivity

class StudentProfileActivity: AppCompatActivity(){

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btHome = findViewById<ImageButton>(R.id.btHome)

        val btTutorList = findViewById<ImageButton>(R.id.btTutors)

        val btEditS = findViewById<Button>(R.id.btEditS)

        val btReviewsS = findViewById<Button>(R.id.btReviewsS)

        val btExitS = findViewById<Button>(R.id.btExitS)

        btHome.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btTutorList.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, TutorListActivity::class.java)
            startActivity(intent)
        }

        btEditS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, StudentProfileEditActivity::class.java)
            startActivity(intent)
        }

        btReviewsS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, StudentsReviewsListActivity::class.java)
            startActivity(intent)
        }

        btExitS.setOnClickListener {
            val intent = Intent(this@StudentProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}