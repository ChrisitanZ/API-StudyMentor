package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.UserTutor
import com.squareup.picasso.Picasso

class TutorAdapter(private val tutors: List<UserTutor>) : RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_tutor, parent, false)
        return TutorViewHolder(view)
    }

    override fun onBindViewHolder(prototype: TutorViewHolder, position: Int) {
        prototype.bind(tutors[position])
    }

    override fun getItemCount(): Int = tutors.size


    class TutorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val email: TextView = itemView.findViewById(R.id.tvEmail)
        private val specialty: TextView = itemView.findViewById(R.id.tvSpecialty)
        private val cost: TextView = itemView.findViewById(R.id.tvFee)
        private val btimage : ImageView = itemView.findViewById(R.id.ivTutor)

        @SuppressLint("SetTextI18n")
        fun bind(tutor: UserTutor){
            name.text = tutor.Name
            email.text = tutor.Email
            specialty.text = tutor.Specialty
            cost.text=tutor.Cost.toString()

            //Picasso.get().load(tutor.Photo).into(btimage)
        }

    }
}




