package com.minnullin.models

import kotlinx.serialization.Serializable

@Serializable
class MediaResponseDto(
    fileName:String,
    type:MediaType
)