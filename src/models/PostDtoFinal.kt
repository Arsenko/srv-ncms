package com.minnullin.models

import java.util.*

class PostDtoFinal(
    val id: Int, //
    val authorName: String?,
    val authorDrawable: Int?,
    val bodyText: String,
    val postDate: Date = Date(),
    val repostPost: Post?,
    val postType: PostType,
    var likeCounter: Int,
    var likedByMe: Boolean = false,
    var commentCounter: Int,
    var shareCounter: Int,
    val location: Pair<Double, Double>?,
    val link: String?,
    var postImage: Int?
) {
    companion object {
        fun generateComp(model: Post,login: String) = model.id?.let {
            var likedByMe=false
            if(model.likedBy!=null) {
                var likedByMe: Boolean = login !in model.likedBy
            }
            PostDtoFinal(
                id = it,
                authorName = model.authorName,
                authorDrawable = model.authorDrawable,
                bodyText = model.bodyText,
                postDate = model.postDate,
                repostPost = model.repostPost,
                postType = model.postType,
                likeCounter = model.likeCounter,
                likedByMe = likedByMe,
                commentCounter = model.commentCounter,
                shareCounter = model.shareCounter,
                location = model.location,
                link = model.link,
                postImage = model.postImage
            )
        }
    }
}