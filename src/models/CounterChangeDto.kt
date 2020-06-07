package com.minnullin.models

import com.minnullin.PostDto
import ru.minnullin.Post

class CounterChangeDto(
    val id:Int,
    val counter:Int,
    val counterType:CounterType
)