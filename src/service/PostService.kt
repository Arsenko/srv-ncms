package com.minnullin.service

import PostRepository
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.Post
import com.minnullin.models.PostType
import com.minnullin.models.PostDtoFinal
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PostService(private val repos: PostRepository) {
    suspend fun getAll(login:String): List<PostDtoFinal?> {
        val temp =repos.getAll()
        return temp.map { PostDtoFinal.generateComp(it,login) }
    }
    suspend fun getById(id:Int): Post? {
        return repos.getById(id)
    }
    suspend fun deleteById(id:Int,authorName: String):HttpStatusCode{
        return repos.deleteById(id,authorName)
    }
    suspend fun addPost(post:PostDtoFinal):Post{
        return repos.addPost(Post.generateComp(post))
    }
    suspend fun changeCounter(model: CounterChangeDto,login: String): PostDtoFinal? {
        return repos.changePostCounter(model.id,model.counter,model.counterType,login)
    }
}