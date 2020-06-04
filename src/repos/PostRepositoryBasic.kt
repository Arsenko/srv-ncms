import com.minnullin.PostDto
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.CounterType
import com.minnullin.models.PostType
import ru.minnullin.Post
import java.util.*

class PostRepositoryBasic :PostRepository{
    var id:Int=-1
    private var postlist= mutableListOf(
        Post(
            getAutoIncrementedId(),
            "netlogy",
            2,
            "First post in our network!",
            Date(),
            null,
            PostType.Post,
            2,
            false,
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
            3,
            true,
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
            4,
            false,
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
    private fun getAutoIncrementedId(): Int {
        id++
        return id
    }
    //работает пока нет удаления, потом переписать
    fun changePostCounter(model:CounterChangeDto):Post{
        var postToChange= postlist[model.id]
        when(model.counterType){
            CounterType.Like->postToChange.likeCounter=model.counter
            CounterType.Dislike->postToChange.dislikeCounter=model.counter
            CounterType.Comment->postToChange.commentCounter=model.counter
            CounterType.Share->postToChange.shareCounter=model.counter
        }
        postlist[model.id]=postToChange
        return postToChange
    }

}