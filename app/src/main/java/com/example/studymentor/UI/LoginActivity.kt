package com.example.studymentor.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.UI.Student.HomeStudentActivity
import com.example.studymentor.UI.Tutor.HomeTutorActivity

class LoginActivity : AppCompatActivity() {
    private var userType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userType = intent.getStringExtra("userType")

        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegisterLogin = findViewById<Button>(R.id.btRegisterLogin)

        btLogin.setOnClickListener {
            val intent = when (userType) {
                "student" -> Intent(this@LoginActivity, HomeStudentActivity::class.java)
                "teacher" -> Intent(this@LoginActivity, HomeTutorActivity::class.java)
                else -> null
            }
            startActivity(intent)
            finish()
        }

        btRegisterLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
