package com.example.studymentor.UI.Student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.studymentor.R
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Tutor
import com.example.studymentor.request.ReviewRequest
import com.example.studymentor.model.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RatingTeacherActivity : AppCompatActivity() {

    private lateinit var tutorSpinner: Spinner
    private lateinit var ratingBar: RatingBar
    private lateinit var reviewText: EditText
    private lateinit var publishButton: Button
    private var tutorList: List<Tutor> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_teacher)

        tutorSpinner = findViewById(R.id.tutor_spinner)
        ratingBar = findViewById(R.id.rating_bar)
        reviewText = findViewById(R.id.review_text)
        publishButton = findViewById(R.id.publish_button)

        fetchTutors()

        publishButton.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val content = reviewText.text.toString()
            val studentId = 25
            val tutorId = tutorList[tutorSpinner.selectedItemPosition].tutorId

            if (content.isEmpty()) {
                Toast.makeText(this, "Completa la reseña antes de publicar", Toast.LENGTH_SHORT).show()
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val currentDate = sdf.format(Date())

                val reviewRequest = ReviewRequest(
                    textMessagge = content,
                    rating = rating,
                    studentId = studentId,
                    tutorId = tutorId,
                    date = currentDate,
                    type = "0"
                )

                RetrofitClient.reviewService.createReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
                    override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@RatingTeacherActivity, "Reseña publicada exitosamente", Toast.LENGTH_SHORT).show()
                            Log.d("Review", "Review posted successfully: ${response.body()?.message}")
                        }
                        // Redirigir a HomeStudentActivity en cualquier caso
                        val intent = Intent(this@RatingTeacherActivity, HomeStudentActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                        Log.e("Review", "Error posting review", t)
                        // Redirigir a HomeStudentActivity en caso de fallo sin mostrar mensaje
                        val intent = Intent(this@RatingTeacherActivity, HomeStudentActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
            }
        }
    }

    private fun fetchTutors() {
        val service = RetrofitClient.tutorService
        service.getTutors().enqueue(object : Callback<List<Tutor>> {
            override fun onResponse(call: Call<List<Tutor>>, response: Response<List<Tutor>>) {
                if (response.isSuccessful) {
                    tutorList = response.body() ?: emptyList()
                    setupSpinner()
                } else {
                    Toast.makeText(this@RatingTeacherActivity, "Error al obtener la lista de tutores", Toast.LENGTH_SHORT).show()
                    Log.e("Tutor", "Failed to fetch tutors: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Tutor>>, t: Throwable) {
                Toast.makeText(this@RatingTeacherActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Tutor", "Error fetching tutors", t)
            }
        })
    }

    private fun setupSpinner() {
        val tutorNames = tutorList.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tutorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tutorSpinner.adapter = adapter
    }
}
