import com.minnullin.models.CounterChangeDto
import com.minnullin.models.CounterType
import io.ktor.features.NotFoundException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.minnullin.Post

class PostRepositoryBasic : PostRepository {
    private var id: Int = -1
    private val mutex = Mutex()
    private var postlist = mutableListOf<Post>()

    override suspend fun getAll(): List<Post> {
        mutex.withLock {
            return postlist.toList()
        }
    }

    private suspend fun getAutoIncrementedId(): Int {
        mutex.withLock {
            id++
            return id
        }
    }

    override suspend fun addPost(post: Post): Post? {
        if (post.id == null) {
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
            return postWithId
        } else return null
    }

    override suspend fun deleteById(id: Int): Boolean {
        for (i in 0 until postlist.size) {
            if (id == postlist[i].id) {
                postlist.removeAt(i)
                return true
            }
        }
        return false
    }

    override suspend fun getById(id: Int): Post? {
        var postToReturn: Post? = null
        for (i in 0 until postlist.size) {
            if (id == postlist[i].id) {
                postToReturn = postlist[i]
            }
        }
        return postToReturn
    }

    override suspend fun changePostCounter(model: CounterChangeDto): Post? {
        mutex.withLock {
            var postToChange: Post? = null
            for (i in 0 until postlist.size) {
                if (model.id == postlist[i].id) {
                    postToChange = postlist[i]
                }
            }
            if (postToChange != null) {
                when (model.counterType) {
                    CounterType.Like -> postToChange.likeChange(model.counter)
                    CounterType.Dislike -> postToChange.dislikeChange(model.counter)
                    CounterType.Comment -> postToChange.commentChange(model.counter)
                    CounterType.Share -> postToChange.shareChange(model.counter)
                }.also {
                    postlist[model.id] = it
                    return it
                }
            }
            return postToChange
        }
    }
}