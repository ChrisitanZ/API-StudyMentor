package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.UI.Student.TutorSchedulesActivity
import com.example.studymentor.model.Tutor

class TutorAdapter(private val tutors: List<Tutor>) : RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_tutor, parent, false)
        return TutorViewHolder(view)

    }

    override fun onBindViewHolder(prototype: TutorViewHolder, position: Int) {
        val tutor = tutors[position]
        prototype.bind(tutor)

        prototype.btSeeMore.setOnClickListener {
            val context = prototype.itemView.context
            val intent = Intent(context, TutorSchedulesActivity::class.java).apply {
                // Pasar la información del tutor a TutorSchedulesActivity
                putExtra("TUTOR_ID", tutor.id)
                putExtra("TUTOR_NAME", tutor.name)
                putExtra("TUTOR_EMAIL", tutor.email)
                putExtra("TUTOR_SPECIALTY", tutor.specialty)
                putExtra("TUTOR_COST", tutor.cost)
            }
            Log.d("TutorAdapter", "Tutor ID: ${tutor.id}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tutors.size


    class TutorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val email: TextView = itemView.findViewById(R.id.tvEmail)
        private val specialty: TextView = itemView.findViewById(R.id.tvSpecialty)
        private val cost: TextView = itemView.findViewById(R.id.tvFee)
        private val btimage : ImageView = itemView.findViewById(R.id.ivTutor)
        val btSeeMore: Button = itemView.findViewById(R.id.btSeeMore)

        @SuppressLint("SetTextI18n")
        fun bind(tutor: Tutor){
            name.text = tutor.name
            email.text = tutor.email
            specialty.text = tutor.specialty
            cost.text=tutor.cost.toString()
            //Picasso.get().load(tutor.Photo).into(btimage)

        }

    }
}




