package com.example.studymentor.UI.Tutor

import android.annotation.SuppressLint
import android.content.Intent
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TutorsReviewsListActivity : AppCompatActivity() {
    lateinit var reviewAdapterTutor: ReviewAdapterTutor
    private lateinit var rvListStudents: RecyclerView
    private lateinit var tvTutorInfo: TextView

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

        val btHomeT = findViewById<ImageButton>(R.id.btHomeT)
        val btProfileT = findViewById<ImageButton>(R.id.btProfileT)
        val btCalendar = findViewById<ImageButton>(R.id.btCalendarT)
        val btListStudent = findViewById<ImageButton>(R.id.btStudents)
        val btSeeReviewsT = findViewById<Button>(R.id.btSeeReviewsT)
        val tvTextReview = findViewById<TextView>(R.id.tvTextReview)
        val ivTutorInfo = findViewById<ImageView>(R.id.ivTutorInfo)
        tvTutorInfo = findViewById<TextView>(R.id.tvTutorInfo)
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
        val tutorId = 1 //Reemplazar por el adecuado

        val service = RetrofitClient.tutorService
        service.getTutorById(tutorId).enqueue(object : Callback<Tutor> {
            override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                if (response.isSuccessful) {
                    val tutor = response.body()
                    if (tutor != null) {
                        tvTutorInfo.text = "${tutor.name} ${tutor.lastname}"
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
                    reviewAdapterTutor = ReviewAdapterTutor(reviews)
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