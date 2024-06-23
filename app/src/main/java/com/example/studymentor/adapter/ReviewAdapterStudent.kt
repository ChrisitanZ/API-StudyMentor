package com.example.studymentor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.apiservice.RetrofitClient
import com.example.studymentor.model.Review
import com.example.studymentor.model.Tutor
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewAdapterStudent(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapterStudent.ReviewViewHolder>() {
    //private val filteredReviews = reviews.filter { it.type == 1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_student_reviews, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        //val review = filteredReviews[position]
        val review = reviews[position]
        holder.bind(review)
    }

    //override fun getItemCount(): Int = filteredReviews.size
    override fun getItemCount(): Int = reviews.size

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvNameRS)
        private val body: TextView = itemView.findViewById(R.id.tvBodyRS)
        private val image: ImageView = itemView.findViewById(R.id.imageView3)
        private val star: ImageView = itemView.findViewById(R.id.ivStarR)
        private val score: TextView = itemView.findViewById(R.id.ScoreS)

        fun bind(review: Review) {
            body.text = review.textMessagge
            score.text = review.rating.toString()

            val service = RetrofitClient.tutorService
            service.getTutorById(review.tutorId).enqueue(object : Callback<Tutor> {
                override fun onResponse(call: Call<Tutor>, response: Response<Tutor>) {
                    if (response.isSuccessful) {
                        val tutor = response.body()
                        if (tutor != null) {
                            name.text = tutor.name
                            Picasso.get()
                                .load(tutor.image)
                                .into(image)
                        } else {
                            name.text = "Tutor Not Found"
                        }
                    } else {
                        name.text = "Tutor Not Found"
                    }
                }

                override fun onFailure(call: Call<Tutor>, t: Throwable) {
                    name.text = "Tutor Not Found"
                }
            })
        }
    }
}