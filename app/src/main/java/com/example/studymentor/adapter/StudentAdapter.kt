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
            name.text = student.name
            age.text = student.birthday
            cellphone.text = student.cellphone

            // Aquí puedes añadir la lógica para cargar la imagen si es necesario, por ejemplo con Picasso o Glide.
            // Picasso.get().load(student.photoUrl).into(image)
        }
    }
}
