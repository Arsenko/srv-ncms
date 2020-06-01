package ru.minnullin

import com.minnullin.models.PostType
import java.util.*

data class Post(
    val id:Long,
    val authorName: String,
    val authorDrawable: Int,
    val bodyText: String,
    val postDate: Date = Date(),
    val repostPost:Post?,
    val postType: PostType,
    var likeCounter: Int,
    var likedByMe: Boolean = false,
    var commentCounter: Int,
    var shareCounter: Int,
    val location: Pair<Double, Double>?,
    val link: String?,
    var postImage: Int?

) {
    fun likeIncrease() {
        likeCounter = likeCounter.inc()
    }

    fun likeDecrease() {
        likeCounter = likeCounter.dec()
    }

    fun commentIncrease() {
        commentCounter = commentCounter.inc()
    }

    fun shareIncrease() {
        shareCounter = shareCounter.inc()
    }
}