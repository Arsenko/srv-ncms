package com.minnullin.service

import PostRepository
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.Post
import com.minnullin.models.PostType
import com.minnullin.models.PostV2Dto
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PostService(private val repos: PostRepository) {
    init {
        val debugListOfPost = mutableListOf(
            Post(
                null,
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
                null,
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
                null,
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
                null,
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
        CoroutineScope(Dispatchers.IO).launch {
            for (i in debugListOfPost) {
                repos.addPost(i)
            }
        }
    }
    suspend fun getAll(): List<PostV2Dto?> {
        return repos.getAll().map(PostV2Dto.Companion::generateComp)
    }
    suspend fun getById(id:Int): Post? {
        return repos.getById(id)
    }
    suspend fun deleteById(id:Int,authorName: String):HttpStatusCode{
        return repos.deleteById(id,authorName)
    }
    suspend fun addPost(post:Post):Post{
        return repos.addPost(post)
    }
    suspend fun changeCounter(model: CounterChangeDto): Post? {
        return repos.changePostCounter(model.id,model.counter,model.counterType)
    }
}