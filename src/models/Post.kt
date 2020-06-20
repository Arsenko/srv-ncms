package com.minnullin.models

import java.util.*

data class Post(
    val id:Int?,
    val authorName: String,
    val authorDrawable: Int,
    val bodyText: String,
    val postDate: Date = Date(),
    val repostPost: Post?,
    val postType: PostType,
    val dislikeCounter:Int,
    val dislikedByMe:Boolean = false,
    val likeCounter: Int,
    val likedByMe: Boolean = false,
    val commentCounter: Int,
    val shareCounter: Int,
    val location: Pair<Double, Double>?,
    val link: String?,
    val postImage: Int?

) {
    fun likeChange(counter:Int): Post =
        copy(likeCounter=counter)

    fun dislikeChange(counter:Int): Post =
        copy(dislikeCounter=counter)
    fun commentChange(counter:Int) : Post =
        copy(commentCounter = counter)

    fun shareChange(counter:Int) : Post =
        copy(shareCounter = counter)
}