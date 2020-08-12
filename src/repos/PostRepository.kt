
import com.minnullin.models.CounterType
import com.minnullin.models.Post
import com.minnullin.models.PostDtoFinal
import io.ktor.http.HttpStatusCode

interface PostRepository{
    suspend fun getAll():List<Post>
    suspend fun changePostCounter(id:Int,counter:Int,counterType:CounterType,login:String): PostDtoFinal?
    suspend fun addPost(post: Post): Post
    suspend fun deleteById(id:Int,authorName:String):HttpStatusCode
    suspend fun getById(id:Int):Post


}