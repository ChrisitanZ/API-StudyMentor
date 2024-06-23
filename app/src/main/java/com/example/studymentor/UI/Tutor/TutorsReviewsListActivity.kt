package com.example.studymentor.UI.Tutor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.adapter.ReviewAdapterTutor
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Review
import com.example.studymentor.model.Tutor
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorsReviewsListActivity : AppCompatActivity() {
    lateinit var reviewAdapterTutor: ReviewAdapterTutor
    private lateinit var rvListStudents: RecyclerView
    private lateinit var tvTutorInfo: TextView
    private lateinit var ivTutorInfo: ImageView
    private lateinit var tvScoreT: TextView
    //Reemplazar por el adecuado
    //private var tutorId: Int = 2
    private lateinit var sharedPreferences: SharedPreferences
    private var tutorId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutors_reviews_list)
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

        val btHomeT = findViewById<ImageButton>(R.id.btHomeT)
        val btProfileT = findViewById<ImageButton>(R.id.btProfileT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btListStudent = findViewById<ImageButton>(R.id.btStudents)
        val btSeeReviewsT = findViewById<Button>(R.id.btSeeReviewsT)
        val tvTextReview = findViewById<TextView>(R.id.tvTextReview)
        ivTutorInfo = findViewById<ImageView>(R.id.ivTutorInfo)
        tvTutorInfo = findViewById<TextView>(R.id.tvTutorInfo)
        tvScoreT = findViewById(R.id.tvScoreT)
        val ivStarReview = findViewById<ImageView>(R.id.ivStarReview)
        val tvScoreT = findViewById<TextView>(R.id.tvScoreT)

        rvListStudents = findViewById(R.id.rvListStudents)
        rvListStudents.layoutManager = LinearLayoutManager(this@TutorsReviewsListActivity)


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

        btCalendar.setOnClickListener{
            val intent = Intent(this@TutorsReviewsListActivity, TutorCalendarActivity::class.java)
            startActivity(intent)
        }

        btSeeReviewsT.setOnClickListener {
            fetchReviewsTutor()
        }

        fetchTutorName()
    }

    private fun fetchTutorName() {
        val service = RetrofitClient.tutorService
        service.getTutorById(tutorId).enqueue(object : Callback<Tutor> {
            override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                if (response.isSuccessful) {
                    val tutor = response.body()
                    if (tutor != null) {
                        tvTutorInfo.text = "${tutor.name} ${tutor.lastname}"
                        Picasso.get()
                            .load(tutor.image)
                            .into(ivTutorInfo)

                        fetchReviewsTutor()
                    }
                } else {
                    Toast.makeText(this@TutorsReviewsListActivity, "Error al obtener el tutor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Tutor>, t: Throwable) {
                Toast.makeText(this@TutorsReviewsListActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchReviewsTutor() {
        val service = RetrofitClient.reviewService
        service.getReviews().enqueue(object : Callback<List<Review>> {
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>){
                if (response.isSuccessful) {
                    val reviews = response.body() ?: emptyList()

                    val filteredReviews = reviews.filter { it.type == 0 && it.tutorId == tutorId }
                    val averageScore = if (filteredReviews.isNotEmpty()) {
                        filteredReviews.map { it.rating }.average()
                    } else {
                        0.0
                    }
                    tvScoreT.text = "Promedio: %.2f".format(averageScore)

                    reviewAdapterTutor = ReviewAdapterTutor(filteredReviews)
                    rvListStudents.adapter = reviewAdapterTutor
                    rvListStudents.layoutManager = LinearLayoutManager(this@TutorsReviewsListActivity)
                } else {
                    Toast.makeText(this@TutorsReviewsListActivity, "Error al obtener la lista de reseñas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Toast.makeText(this@TutorsReviewsListActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}