import com.minnullin.models.CounterChangeDto
import com.minnullin.models.CounterType
import com.minnullin.models.Post
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.NotSerializableException

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

    override suspend fun addPost(post: Post): Post {
        mutex.withLock {
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
            } else throw NotSerializableException()
        }
    }

    override suspend fun deleteById(id: Int,authorName:String): HttpStatusCode {
        mutex.withLock {
            val findPost=postlist.find{
                it.id==id
            }
            if (findPost != null) {
                if(findPost.authorName!=authorName){
                    return HttpStatusCode.Forbidden
                }
                for (i in 0 until postlist.size) {
                    if (id == postlist[i].id) {
                        postlist.removeAt(i)
                        return HttpStatusCode.Accepted
                    }
                }
            }
            return HttpStatusCode.NotFound
        }
    }

    override suspend fun getById(id: Int): Post {
        mutex.withLock{
            var postToReturn: Post? = null
            for (i in 0 until postlist.size) {
                if (id == postlist[i].id) {
                    postToReturn = postlist[i]
                }
            }
            if (postToReturn == null) {
                throw NotFoundException()
            }
            return postToReturn
        }
    }

    override suspend fun changePostCounter(id:Int,counter:Int,counterType:CounterType): Post? {
        mutex.withLock {
            var postToChange: Post? = null
            for (i in 0 until postlist.size) {
                if (id == postlist[i].id) {
                    postToChange = postlist[i]
                }
            }
            if (postToChange != null) {
                when (counterType) {
                    CounterType.Like -> postToChange.likeChange(counter)
                    CounterType.Dislike -> postToChange.dislikeChange(counter)
                    CounterType.Comment -> postToChange.commentChange(counter)
                    CounterType.Share -> postToChange.shareChange(counter)
                }.also {
                    postlist[id] = it
                    return it
                }
            }
            return postToChange
        }
    }
}