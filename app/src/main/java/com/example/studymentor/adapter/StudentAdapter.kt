package com.example.studymentor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Student
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class StudentAdapter(private val students: List<Student>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvStudentName)
        private val age: TextView = itemView.findViewById(R.id.tvAge)
        private val cellphone: TextView = itemView.findViewById(R.id.tvCellphonePS)
        private val image: ImageView = itemView.findViewById(R.id.ivStudent)
        private val button: Button = itemView.findViewById(R.id.btMore)

        @SuppressLint("SetTextI18n")
        fun bind(student: Student) {
            name.text = "${student.name} ${student.lastName}"
            age.text = calculateAge(student.birthday)
            cellphone.text = student.cellphone

            Picasso.get()
                .load(student.image)
                .into(image)
        }

        private fun calculateAge(birthday: String): String {
            val birthDate = LocalDate.parse(birthday.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
            val currentDate = LocalDate.now()
            val age = ChronoUnit.YEARS.between(birthDate, currentDate)
            return "$age years"
        }
    }
}
