import com.example.studymentor.model.Score
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ScoreService {
    @POST("scores")
    fun addScore(@Body score: Score): Call<Score>
}