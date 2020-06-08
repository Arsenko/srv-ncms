import com.minnullin.models.CounterChangeDto
import com.minnullin.models.CounterType
import com.minnullin.models.PostType
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.minnullin.Post
import java.util.*

class PostRepositoryBasic : PostRepository {
    var id: Int = -1
    private val mutex = Mutex()
    private var postlist = mutableListOf<Post>()

    override suspend fun getAll(): List<Post> {
        return postlist
    }

    private suspend fun getAutoIncrementedId(): Int {
        mutex.withLock {
            id++
            return id
        }
    }

    suspend fun addPost(post: Post): Boolean {
        return if (post.id == null) {
            val postWithId = Post(
                id = getAutoIncrementedId(),
                authorName = post.authorName,
                authorDrawable = post.authorDrawable,
                bodyText = post.bodyText,
                postDate = post.postDate,
                repostPost = post.repostPost,
                postType = post.postType,
                dislikeCounter = post.dislikeCounter,
                dislikedByMe = post.dislikedByMe,
                likeCounter = post.likeCounter,
                likedByMe = post.likedByMe,
                commentCounter = post.commentCounter,
                shareCounter = post.shareCounter,
                location = post.location,
                link = post.link,
                postImage = post.postImage
            )
            postlist.add(postWithId)
            true
        } else false
    }

    //работает пока нет удаления, потом переписать
    suspend fun changePostCounter(model: CounterChangeDto): Boolean {
        mutex.withLock {
            return try {
                var postToChange = postlist[model.id]
                when (model.counterType) {
                    CounterType.Like -> postToChange = postToChange.likeChange(model.counter)
                    CounterType.Dislike -> postToChange = postToChange.dislikeChange(model.counter)
                    CounterType.Comment -> postToChange = postToChange.commentChange(model.counter)
                    CounterType.Share -> postToChange = postToChange.shareChange(model.counter)
                    else -> {
                    }
                }
                postlist[model.id] = postToChange
                true
            } catch (e: Exception) {
                false
            }
        }
    }

}