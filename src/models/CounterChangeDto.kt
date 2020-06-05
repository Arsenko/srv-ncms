package com.minnullin.models

import com.minnullin.PostDto
import ru.minnullin.Post

class CounterChangeDto(
    val id:Int,
    val counter:Int,
    val counterType:CounterType
) {
    companion object {
        fun generateClass(model: CounterChangeDto) = CounterChangeDto(
            id = model.id,
            counter = model.counter,
            counterType = model.counterType
        )
    }
}