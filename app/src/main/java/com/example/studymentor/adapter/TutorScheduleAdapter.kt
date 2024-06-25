import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymentor.R
import com.example.studymentor.UI.PaymentActivity
import com.example.studymentor.model.Schedule

class ScheduleAdapter(private val context: Context, private val scheduleList: List<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val days: TextView = itemView.findViewById(R.id.tv_days)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val tutorName: TextView = itemView.findViewById(R.id.tv_tutor_name)
        val cost: TextView = itemView.findViewById(R.id.tv_cost)
        val paymentButton: Button = itemView.findViewById(R.id.btn_payment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.prototype_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]
        val formattedDate = formatDate(schedule.day)
        val formattedStartTime = formatTime(schedule.startingHour)

        holder.days.text = formattedDate
        holder.time.text = formattedStartTime
        holder.cost.text = "${schedule.tutorHours} hours"
        holder.tutorName.text = "Cost: S/. ${schedule.price}"

        holder.paymentButton.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("SCHEDULE_ID", schedule.id)
                putExtra("SCHEDULE_COST", schedule.price)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = scheduleList.size

    private fun formatDate(date: String): String {
        val parts = date.split("-")
        if (parts.size == 3) {
            val day = parts[2]
            val month = parts[1]
            val year = parts[0]
            return "$day-$month-$year"
        }
        return date // En caso de error, retornar la fecha original
    }

    private fun formatTime(time: String): String {
        val parts = time.split(":")
        if (parts.size >= 2) {
            val hour = parts[0]
            val minute = parts[1]
            return "$hour:$minute"
        }
        return time // En caso de error, retornar la hora original
    }
}
