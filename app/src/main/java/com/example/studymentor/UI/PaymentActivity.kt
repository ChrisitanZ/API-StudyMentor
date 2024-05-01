package com.example.studymentor.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Agregar a los activies restantes
        val btTutor = findViewById<ImageButton>(R.id.btTutor)
        val btHome = findViewById<ImageButton>(R.id.btHome)

        btHome.setOnClickListener {
            val intent = Intent(this@PaymentActivity, HomeStudentActivity::class.java)
            startActivity(intent)
        }
        btTutor.setOnClickListener {
            val intent = Intent(this@PaymentActivity, TutorListActivity::class.java)
            startActivity(intent)
        }
    }
}