package com.minnullin.models

import com.minnullin.PostDto
import kotlinx.serialization.Serializable
import ru.minnullin.Post
@Serializable
class CounterChangeDto(
    val id:Int,
    val counter:Int,
    val counterType:CounterType
)