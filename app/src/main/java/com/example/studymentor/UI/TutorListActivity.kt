package com.example.studymentor.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.activity.FindTutors

class TutorListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Agregar a los activies restantes
        val btHome = findViewById<ImageButton>(R.id.btHome)
        val btFind = findViewById<Button>(R.id.btSearch)

        btHome.setOnClickListener {
            val intent = Intent(this@TutorListActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btFind.setOnClickListener{
            val intent = Intent(this@TutorListActivity, FindTutors::class.java)
            startActivity(intent)
        }

    }


}