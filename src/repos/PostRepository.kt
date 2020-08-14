
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.CounterType
import com.minnullin.models.Post
import com.minnullin.models.PostDtoFinal
import io.ktor.http.HttpStatusCode

interface PostRepository{
    suspend fun getAll():List<Post>
    suspend fun changePostCounter(model:CounterChangeDto,login:String): PostDtoFinal?
    suspend fun addPost(post: PostDtoFinal): Post
    suspend fun deleteById(id:Int,authorName:String):HttpStatusCode
    suspend fun getById(id:Int):Post


}