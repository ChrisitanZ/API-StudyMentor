package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Tutor
import com.squareup.picasso.Picasso

class TutorAdapter(private val tutors: List<Tutor>) : RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

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
        //private val cost: TextView = itemView.findViewById(R.id.tvFee)
        private val btimage : ImageView = itemView.findViewById(R.id.ivTutor)

        @SuppressLint("SetTextI18n")
        fun bind(tutor: Tutor){
            name.text = "${tutor.name} ${tutor.lastname}"
            email.text = tutor.email
            specialty.text = tutor.specialty
            //cost.text=tutor.cost.toString()

            Picasso.get()
                .load(tutor.image)
                .into(btimage)
        }
    }
}




