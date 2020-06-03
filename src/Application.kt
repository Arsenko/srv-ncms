package com.minnullin

import PostRepositoryBasic
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.PostType
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import ru.minnullin.Post
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val repos=PostRepositoryBasic()
    install(Routing) {
        route("/api/v1/posts/") {
            get {
                val respond=repos.getAll()//.map(PostDto.Companion::generateComp)
                call.respond(respond)
            }
        }
        route("/api/v1/posts/changeCounter"){
            post {
                val input=call.receive<CounterChangeDto>()
                val model=CounterChangeDto(input.id,input.counter,input.counterType)
                call.respond(repos.changePostCounter(model))
            }
        }
        route("/"){
            get{
                call.respond(HttpStatusCode.Accepted,"test completed")
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
}

class PostDto(
    val id: Int, //
    val authorName: String, //
    val authorDrawable: Int,  //
    val bodyText: String,  //
    val postDate: Date = Date(), //
    val repostPost: Post?,  //
    val postType: PostType,  //
    var dislikeCounter: Int,  //
    var dislikedByMe: Boolean = false,  //
    var likeCounter: Int,  //
    var likedByMe: Boolean = false,  //
    var commentCounter: Int,  //
    var shareCounter: Int,  //
    val location: Pair<Double, Double>?,  //
    val link: String?,  //
    var postImage: Int?  //
) {
    companion object {
        fun generateComp(model: Post) = PostDto(
            id = model.id,
            authorName = model.authorName,
            authorDrawable = model.authorDrawable,
            bodyText = model.bodyText,
            postDate = model.postDate,
            repostPost = model.repostPost,
            postType = model.postType,
            dislikeCounter = model.dislikeCounter,
            dislikedByMe = model.dislikedByMe,
            likeCounter = model.likeCounter,
            likedByMe = model.likedByMe,
            commentCounter = model.commentCounter,
            shareCounter = model.shareCounter,
            location = model.location,
            link = model.link,
            postImage = model.postImage
        )
    }
}

