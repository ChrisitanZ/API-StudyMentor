package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.UserTutor

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
    val name: TextView = itemView.findViewById(R.id.tvName)
    val email: TextView = itemView.findViewById(R.id.tvEmail)
    val specialty: TextView = itemView.findViewById(R.id.tvSpecialty)
    val cost: TextView = itemView.findViewById(R.id.tvFee)

        @SuppressLint("SetTextI18n")
        fun bind(tutor: UserTutor){
            name.text = tutor.Name
            email.text = tutor.Email
            specialty.text = tutor.Specialty
            cost.text=tutor.Cost.toString()
        }

    }
}




