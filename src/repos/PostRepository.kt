import com.minnullin.PostDto
import ru.minnullin.Post

interface PostRepository{
    suspend fun getAll():List<Post>
}