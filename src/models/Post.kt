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
    fun likeChange(counter:Boolean,login:String): Post {
        if(counter) {
            return Post(
                    id = id,
                    authorName = authorName,
                    authorDrawable = authorDrawable,
                    bodyText = bodyText,
                    postDate = postDate,
                    repostPost = repostPost,
                    postType = postType,
                    likeCounter = likeCounter.inc(),
                    likedBy = addToLikeList(likedBy, login),
                    commentCounter = commentCounter,
                    shareCounter = shareCounter,
                    location = location,
                    link = link,
                    postImage = postImage
            )
        }else{
            return Post(
                    id = id,
                    authorName = authorName,
                    authorDrawable = authorDrawable,
                    bodyText = bodyText,
                    postDate = postDate,
                    repostPost = repostPost,
                    postType = postType,
                    likeCounter = likeCounter.dec(),
                    likedBy = addToLikeList(likedBy, login),
                    commentCounter = commentCounter,
                    shareCounter = shareCounter,
                    location = location,
                    link = link,
                    postImage = postImage
            )
        }
    }

    fun commentChange(counter:Boolean,login:String) : Post {
        if(counter) {
            return copy(commentCounter = commentCounter.inc())
        }else{
            return copy(commentCounter = commentCounter.dec())
        }
    }

    fun shareChange(counter:Boolean,login:String) : Post {
        if(counter) {
            return Post(
                    id = id,
                    authorName = authorName,
                    authorDrawable = authorDrawable,
                    bodyText = bodyText,
                    postDate = postDate,
                    repostPost = repostPost,
                    postType = postType,
                    likeCounter = likeCounter,
                    likedBy = likedBy,
                    commentCounter = commentCounter,
                    shareCounter = shareCounter.inc(),
                    location = location,
                    link = link,
                    postImage = postImage
            )
        }else{
            return Post(
                    id = id,
                    authorName = authorName,
                    authorDrawable = authorDrawable,
                    bodyText = bodyText,
                    postDate = postDate,
                    repostPost = repostPost,
                    postType = postType,
                    likeCounter = likeCounter,
                    likedBy = likedBy,
                    commentCounter = commentCounter,
                    shareCounter = shareCounter.dec(),
                    location = location,
                    link = link,
                    postImage = postImage
            )
        }
    }

    private fun addToLikeList(likedBy: MutableList<String>?, newLikeLogin:String):MutableList<String>{
        var temp=likedBy
        if(temp!=null) {
            temp.add(newLikeLogin)
        }else{
            temp= mutableListOf(newLikeLogin)
        }
        return temp
    }

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