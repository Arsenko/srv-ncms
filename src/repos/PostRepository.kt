import com.minnullin.PostDto
import com.minnullin.models.CounterChangeDto
import ru.minnullin.Post

interface PostRepository{
    suspend fun getAll():List<Post>
    suspend fun changePostCounter(model: CounterChangeDto): Post?
    suspend fun addPost(post: Post): Post?
    suspend fun deleteById(id:Int):Boolean
    suspend fun getById(id:Int):Post?
}