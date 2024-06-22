import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.model.Schedule

class ScheduleAdapter(private val scheduleList: List<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val daysTextView: TextView = itemView.findViewById(R.id.tv_days)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
        val tutorNameTextView: TextView = itemView.findViewById(R.id.tv_tutor_name)
        val priceTextView: TextView = itemView.findViewById(R.id.tv_cost)
        val paymentButton: Button = itemView.findViewById(R.id.btn_payment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.prototype_schedule, parent, false)
        return ScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentItem = scheduleList[position]
        holder.daysTextView.text = currentItem.days
        holder.timeTextView.text = currentItem.time
        holder.tutorNameTextView.text = currentItem.tutorName
        holder.priceTextView.text = currentItem.price
        holder.paymentButton.setOnClickListener {

        }
    }

    override fun getItemCount() = scheduleList.size
}
