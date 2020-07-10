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