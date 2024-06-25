package com.example.studymentor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Review
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Student
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ReviewAdapterTutor(private val reviews: List<Review>): RecyclerView.Adapter<ReviewAdapterTutor.ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_tutor_reviews, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = reviews.size

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvNameRT)
        private val body: TextView = itemView.findViewById(R.id.tvBodyRT)
        private val image: ImageView = itemView.findViewById(R.id.imageView7)
        private val star: ImageView = itemView.findViewById(R.id.ivStarR)
        private val score: TextView = itemView.findViewById(R.id.scoreT)

        fun bind(review: Review) {
            //name.text = review.studentId.toString()
            body.text = review.textMessagge
            score.text = review.rating.toString()


            val service = RetrofitClient.studentService
            service.getStudentById(review.studentId).enqueue(object : Callback<Student> {
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if (response.isSuccessful) {
                        val student = response.body()
                        if (student != null) {
                            name.text = "${student.name} ${student.lastname}"
                            Picasso.get()
                                .load(student.image)
                                .into(image)
                        }
                    } else  {
                        name.text = "Student Not Found"
                    }
                }

                override fun onFailure(p0: Call<Student>, p1: Throwable) {
                    name.text = "Student Not Found"
                }
            })
        }
    }
}