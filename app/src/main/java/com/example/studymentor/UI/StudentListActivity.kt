package com.example.studymentor.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.adapter.StudentAdapter

class StudentListActivity: AppCompatActivity() {

    lateinit var studentAdapter: StudentAdapter
    private lateinit var rvStudents: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val btHome = findViewById<ImageButton>(R.id.btHome)
        rvStudents = findViewById(R.id.rvSudents)

        btHome.setOnClickListener {
            val intent = Intent(this@StudentListActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }


    }
}