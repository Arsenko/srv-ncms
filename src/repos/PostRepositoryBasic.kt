import com.minnullin.models.PostType
import ru.minnullin.Post
import java.util.*

class PostRepositoryBasic :PostRepository{
    var id:Long=0
    var postlist= listOf(
        Post(
            getAutoIncrementedId(),
            "netlogy",
            2,
            "First post in our network!",
            Date(),
            null,
            PostType.Post,
            0,
            false,
            8,
            2,
            null,
            null,
            null
        ),
        Post(
            getAutoIncrementedId(),
            "etlogy",
            2,
            "Second post in our network!",
            Date(),
            null,
            PostType.Event,
            0,
            false,
            8,
            2,
            Pair(60.0, 85.0),
            null,
            null
        ),
        Post(
            getAutoIncrementedId(),
            "tlogy",
            2,
            "Third post in our network!",
            Date(),
            null,
            PostType.Video,
            0,
            false,
            8,
            2,
            null,
            "https://www.youtube.com/watch?v=lO5_E9aObE0",
            null
        ),
        Post(
            getAutoIncrementedId(),
            "logy",
            2,
            "Fourth post in our network!",
            Date(),
            null,
            PostType.Advertising,
            0,
            false,
            8,
            2,
            null,
            "https://l.netology.ru/marketing_director_guide?utm_source=vk&utm_medium=smm&utm_campaign=bim_md_oz_vk_smm_guide&utm_content=12052020",
            1
        )
    )
    override suspend fun getAll(): List<Post> {
        return postlist
    }
    private fun getAutoIncrementedId(): Long {
        id++
        return id
    }

}