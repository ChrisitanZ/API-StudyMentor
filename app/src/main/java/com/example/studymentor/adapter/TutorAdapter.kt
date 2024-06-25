package com.example.studymentor.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.UI.Student.TutorSchedulesActivity
import com.example.studymentor.model.Tutor
import com.squareup.picasso.Picasso

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
                putExtra("TUTOR_ID", tutor.tutorId)
                putExtra("TUTOR_NAME", tutor.name)
                putExtra("TUTOR_EMAIL", tutor.email)
                putExtra("TUTOR_SPECIALTY", tutor.specialty)
                putExtra("TUTOR_COST", tutor.cost)
            }
            Log.d("TutorAdapter", "Tutor ID: ${tutor.tutorId}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tutors.size

    class TutorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val email: TextView = itemView.findViewById(R.id.tvEmail)
        private val specialty: TextView = itemView.findViewById(R.id.tvSpecialty)
        private val btimage: ImageView = itemView.findViewById(R.id.ivTutor)
        val btSeeMore: Button = itemView.findViewById(R.id.btSeeMore)

        fun bind(tutor: Tutor) {
            name.text = "${tutor.name} ${tutor.lastname}"
            email.text = tutor.email
            specialty.text = tutor.specialty

<<<<<<< Updated upstream
            cost.text=tutor.cost.toString()
            //Picasso.get().load(tutor.Photo).into(btimage)
            //cost.text=tutor.cost.toString()

            Picasso.get()
                .load(tutor.image)
                .into(btimage)
=======
            Picasso.get().load(tutor.image).into(btimage)
>>>>>>> Stashed changes
        }
    }
}
