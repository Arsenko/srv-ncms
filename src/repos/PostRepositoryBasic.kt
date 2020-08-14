import com.minnullin.models.*
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.NotSerializableException
import java.util.*

class PostRepositoryBasic : PostRepository {
    private var id: Int = 3
    private val mutex = Mutex()
    private var postlist = mutableListOf<Post>(
            Post(
                    0,
                    "netlogy",
                    2,
                    "First post in our network!",
                    Date(),
                    null,
                    PostType.Post,
                    2,
                    mutableListOf("aaa"),
                    0,
                    8,
                    null,
                    null,
                    null
            ),
            Post(
                    1,
                    "etlogy",
                    2,
                    "Second post in our network!",
                    Date(),
                    null,
                    PostType.Event,
                    3,
                    mutableListOf(),
                    0,
                    2,
                    Pair(60.0, 85.0),
                    null,
                    null
            ),
            Post(
                    2,
                    "tlogy",
                    2,
                    "Third post in our network!",
                    Date(),
                    null,
                    PostType.Video,
                    4,
                    mutableListOf(),
                    0,
                    2,
                    null,
                    "https://www.youtube.com/watch?v=lO5_E9aObE0",
                    null
            ),
            Post(
                    3,
                    "logy",
                    2,
                    "Fourth post in our network!",
                    Date(),
                    null,
                    PostType.Advertising,
                    0,
                    mutableListOf("aaa"),
                    0,
                    2,
                    null,
                    "https://l.netology.ru/marketing_director_guide?utm_source=vk&utm_medium=smm&utm_campaign=bim_md_oz_vk_smm_guide&utm_content=12052020",
                    1
            )
    )

    override suspend fun getAll(): List<Post> {
        return postlist.toList()
    }

    private suspend fun getAutoIncrementedId(): Int {
        mutex.withLock {
            id++
            return id
        }
    }

    override suspend fun addPost(post: Post): Post {
        if (post.id == null) {
            val postWithId = Post(
                    id = getAutoIncrementedId(),
                    authorName = post.authorName,
                    authorDrawable = post.authorDrawable,
                    bodyText = post.bodyText,
                    postDate = post.postDate,
                    repostPost = post.repostPost,
                    postType = post.postType,
                    likeCounter = post.likeCounter,
                    likedBy = null,
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

    override suspend fun deleteById(id: Int, authorName: String): HttpStatusCode {
        mutex.withLock {
            val findPost = postlist.find {
                it.id == id
            }
            if (findPost != null) {
                if (findPost.authorName != authorName) {
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
        mutex.withLock {
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

    override suspend fun changePostCounter(model:CounterChangeDto,login: String): PostDtoFinal? {
        mutex.withLock {
            var postToChange: Post? = null
            for (i in 0 until postlist.size) {
                if (id == postlist[i].id) {
                    postToChange = postlist[i]
                }
            }
            if (postToChange != null) {
                when (model.counterType) {
                    CounterType.Like -> postToChange.likeChange(model.counter,login)
                    CounterType.Comment -> postToChange.commentChange(model.counter,login)
                    CounterType.Share -> postToChange.shareChange(model.counter,login)
                    else -> postToChange
                }.also {
                    postlist[id] = it
                    return PostDtoFinal.generateComp(it,login)
                }
            }
            return postToChange?.let { PostDtoFinal.generateComp(it,login) }
        }
    }
}