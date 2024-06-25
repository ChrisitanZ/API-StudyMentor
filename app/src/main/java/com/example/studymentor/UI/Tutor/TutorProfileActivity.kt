package com.example.studymentor.UI.Tutor


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studymentor.R
import com.example.studymentor.UI.Student.HomeStudentActivity
import com.example.studymentor.UI.MainActivity
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Tutor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import com.squareup.picasso.Picasso

class TutorProfileActivity : AppCompatActivity() {
    private lateinit var tvname: TextView
    private lateinit var tvcourse: TextView
    private lateinit var tvCellphone: TextView
    private lateinit var tvemail: TextView
    private lateinit var imageProfile: ImageView

    private lateinit var sharedPreferences: SharedPreferences
    private var tutorId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutor_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("com.example.studymentor.session", Context.MODE_PRIVATE)
        tutorId = sharedPreferences.getInt("USER_ID", -1)

        if (tutorId == -1) {
            Toast.makeText(this, "No se pudo obtener el ID del tutor", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si no se encuentra el ID del tutor
            return
        }

        val btHome = findViewById<ImageButton>(R.id.btHomeT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btTutorList = findViewById<ImageButton>(R.id.btStudents)
        val btEdit = findViewById<Button>(R.id.btEdit)
        val btReview = findViewById<Button>(R.id.btReviews)
        val btExitT = findViewById<Button>(R.id.btExitT)

        tvname = findViewById(R.id.tvname)
        tvcourse = findViewById(R.id.tvcourse)
        tvCellphone = findViewById(R.id.tvCellphone)
        tvemail = findViewById(R.id.tvemail)
        imageProfile  = findViewById(R.id.imageProfile)

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

            val intent = Intent(this@TutorProfileActivity, TutorsReviewsListActivity::class.java)
            startActivity(intent)
        }

        btExitT.setOnClickListener {
            val intent = Intent(this@TutorProfileActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btCalendar.setOnClickListener{
            val intent = Intent(this@TutorProfileActivity, TutorCalendarActivity::class.java)
            startActivity(intent)
        }

        fetchInfoTutor()
    }

    private fun fetchInfoTutor() {
        val service = RetrofitClient.tutorService
        service.getTutorById(tutorId).enqueue(object : Callback<Tutor> {
            override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                if (response.isSuccessful) {
                    val tutor = response.body()
                    if (tutor != null) {
                        tvname.text = "Nombre Completo: ${tutor.name} ${tutor.lastname}"
                        tvemail.text = "Email: ${tutor.email}"
                        tvcourse.text = "Curso: ${tutor.specialty}"
                        tvCellphone.text = "Telefono: ${tutor.cellphone}"

                        if (!tutor.image.isNullOrEmpty()) {
                            Picasso.get()
                                .load(tutor.image)
                                .into(imageProfile, object : com.squareup.picasso.Callback {
                                    override fun onSuccess() {
                                        // Imagen cargada exitosamente
                                    }

                                    override fun onError(e: java.lang.Exception?) {
                                        // Error al cargar la imagen, puedes ocultar el ImageView
                                        imageProfile.visibility = View.GONE
                                    }
                                })
                        } else {
                            imageProfile.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(this@TutorProfileActivity, "Error al obtener el tutor", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(p0: Call<Tutor>, t: Throwable) {
                Toast.makeText(this@TutorProfileActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}