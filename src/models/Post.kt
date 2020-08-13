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
    fun likeChange(counter:Int,login:String): Post {
        return Post(
                id = id,
                authorName = authorName,
                authorDrawable = authorDrawable,
                bodyText = bodyText,
                postDate = postDate,
                repostPost = repostPost,
                postType = postType,
                likeCounter = counter,
                likedBy = addToLikeList(likedBy,login),
                commentCounter = commentCounter,
                shareCounter = shareCounter,
                location = location,
                link = link,
                postImage = postImage
        )
    }

    fun commentChange(counter:Int,login:String) : Post =
        copy(commentCounter = counter)

    fun shareChange(counter:Int,login:String) : Post {
        return Post(
                id=id,
                authorName = authorName,
                authorDrawable = authorDrawable,
                bodyText = bodyText,
                postDate = postDate,
                repostPost = repostPost,
                postType = postType,
                likeCounter = likeCounter,
                likedBy = likedBy,
                commentCounter = commentCounter,
                shareCounter = counter,
                location = location,
                link=link,
                postImage = postImage
        )
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