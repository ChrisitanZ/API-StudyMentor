package com.example.studymentor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Score
import com.example.studymentor.model.Student
import com.example.studymentor.model.Tutor
import java.text.SimpleDateFormat
import java.util.Locale

class StudentScoreAdapter(
    private val scores: List<Score>,
    private val tutorList: List<Tutor>
) : RecyclerView.Adapter<StudentScoreAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNoteType: TextView = itemView.findViewById(R.id.tvNoteType)
        val tvNoteDate: TextView = itemView.findViewById(R.id.tvNoteDate)
        val tvNoteScoreValue: TextView = itemView.findViewById(R.id.tvNoteScore)
        val tvNoteStatus: TextView = itemView.findViewById(R.id.tvNoteStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.tvNoteType.text = score.type
        holder.tvNoteDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(score.date)
        holder.tvNoteScoreValue.text = score.scoreValue
        holder.tvNoteStatus.text = score.status

    }

    override fun getItemCount() = scores.size
}
