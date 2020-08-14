package com.minnullin.models

import kotlinx.serialization.Serializable

@Serializable
class CounterChangeDto(
    val id:Int,
    val counter:Boolean,
    val counterType:CounterType
)