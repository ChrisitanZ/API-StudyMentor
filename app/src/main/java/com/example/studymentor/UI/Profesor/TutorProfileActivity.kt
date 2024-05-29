package com.example.studymentor.UI.Profesor


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.UI.Estudiante.HomeStudentActivity
import com.example.studymentor.UI.Estudiante.TutorListActivity

class TutorProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_tutor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btHome = findViewById<ImageButton>(R.id.btHomeT)

        val btTutorList = findViewById<ImageButton>(R.id.btStudents)

        val btEdit = findViewById<Button>(R.id.btEdit)

        val btReview=findViewById<Button>(R.id.btReviews)

        btHome.setOnClickListener {
            val intent = Intent(this@TutorProfileActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btTutorList.setOnClickListener {
            val intent = Intent(this@TutorProfileActivity, StudentListActivity::class.java)
            startActivity(intent)
        }

        btEdit.setOnClickListener {
            val intent = Intent(this@TutorProfileActivity, TutorProfileEditActivity::class.java)
            startActivity(intent)
        }

        btReview.setOnClickListener {

            val intent = Intent(this@TutorProfileActivity, StudentReviewsListActivity::class.java)
            startActivity(intent)
        }
    }
}