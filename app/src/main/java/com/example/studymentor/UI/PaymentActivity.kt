package com.example.studymentor.UI

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.StudentCalendarActivity
import com.example.studymentor.UI.Student.HomeStudentActivity
import com.example.studymentor.UI.Student.StudentProfileActivity
import com.example.studymentor.UI.Student.TutorListActivity
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.apiservice.ScheduleApiService
import com.example.studymentor.model.Schedule
import com.example.studymentor.request.ScheduleRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

    private lateinit var scheduleApiService: ScheduleApiService
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scheduleApiService = RetrofitClient.scheduleService
        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)

        val tvCost = findViewById<TextView>(R.id.tvCost)
        val btSaveP = findViewById<Button>(R.id.btSaveP)
        val scheduleId = intent.getIntExtra("SCHEDULE_ID", -1)
        val scheduleCost = intent.getDoubleExtra("SCHEDULE_COST", 0.0)
        val btHome = findViewById<ImageButton>(R.id.btHome)
        val btTutorList = findViewById<ImageButton>(R.id.btTutors)
        val btPerfil = findViewById<ImageButton>(R.id.btPerfilEstudiante)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendar)

        tvCost.text = "PEN $scheduleCost"
        btSaveP.setOnClickListener {
            processPayment(scheduleId)
        }

        btHome.setOnClickListener {
            val intent = Intent(this, HomeStudentActivity::class.java)
            startActivity(intent)
        }

        btTutorList.setOnClickListener {
            val intent = Intent(this, TutorListActivity::class.java)
            startActivity(intent)
        }

        btPerfil.setOnClickListener {
            val intent = Intent(this, StudentProfileActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener {
            val intent = Intent(this, StudentCalendarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun processPayment(scheduleId: Int) {
        val studentId = getLoggedStudentId()
        val scheduleRequest = ScheduleRequest(studentId, false)

        scheduleApiService.updateSchedule(scheduleId, scheduleRequest).enqueue(object : Callback<Schedule> {
            override fun onResponse(call: Call<Schedule>, response: Response<Schedule>) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@PaymentActivity, "Pago efectuado correctamente", Toast.LENGTH_SHORT).show()
                        finish() // Regresar a la actividad anterior
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@PaymentActivity, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Schedule>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(this@PaymentActivity, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getLoggedStudentId(): Int {
        return sharedPreferences.getInt("USER_ID", -1)
    }
}
