package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Student

class StudentAdapter(private val students: List<Student>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_student, parent, false)
        return StudentAdapter.StudentViewHolder(view)
    }

    override fun onBindViewHolder(prototype: StudentAdapter.StudentViewHolder, position: Int) {
        prototype.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvStudentName)
        private val age: TextView = itemView.findViewById(R.id.tvAge)
        private val course: TextView = itemView.findViewById(R.id.tvCourse)
        private val btimage : ImageView = itemView.findViewById(R.id.ivTutor)

        @SuppressLint("SetTextI18n")
        fun bind(student: Student){
            name.text = student.Name
            age.text = student.Age
            course.text = student.Course

            //Picasso.get().load(tutor.Photo).into(btimage)
        }

    }
}