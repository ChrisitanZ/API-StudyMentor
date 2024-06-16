package com.example.studymentor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Score

class ScoreAdapter(private val scores: List<Score>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.prototype_score, parent, false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.titleTextView.text = score.type
        holder.dateTextView.text = score.date.toString()
        holder.scoreTextView.text = score.scoreValue
        holder.statusTextView.text = score.status
    }

    override fun getItemCount() = scores.size

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvNoteType)
        val dateTextView: TextView = itemView.findViewById(R.id.tvNoteDate)
        val scoreTextView: TextView = itemView.findViewById(R.id.tvNoteScore)
        val statusTextView: TextView = itemView.findViewById(R.id.tvNoteStatus)
    }
}
