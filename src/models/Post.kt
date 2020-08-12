package com.minnullin.models

import java.util.*

data class Post(
    val id:Int?,
    val authorName: String?,
    val authorDrawable: Int?,
    val bodyText: String,
    val postDate: Date = Date(),
    val repostPost: Post?,
    val postType: PostType,
    val likeCounter: Int,
    val likedBy: MutableList<String>?,
    val commentCounter: Int,
    val shareCounter: Int,
    val location: Pair<Double, Double>?,
    val link: String?,
    val postImage: Int?

) {
    fun likeChange(counter:Int): Post =
        copy(likeCounter=counter)

    fun commentChange(counter:Int) : Post =
        copy(commentCounter = counter)

    fun shareChange(counter:Int) : Post =
        copy(shareCounter = counter)

    companion object{
        fun generateComp(model:PostDtoFinal): Post {
            return Post(
                    id = model.id,
                    authorName = model.authorName,
                    authorDrawable = model.authorDrawable,
                    bodyText = model.bodyText,
                    postDate = model.postDate,
                    repostPost = model.repostPost,
                    postType = model.postType,
                    likeCounter = model.likeCounter,
                    likedBy = null,
                    commentCounter = model.commentCounter,
                    shareCounter = model.shareCounter,
                    location = model.location,
                    link=model.link,
                    postImage = model.postImage
            )
        }
    }
}