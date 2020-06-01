package com.minnullin

import PostRepository
import PostRepositoryBasic
import com.google.gson.GsonBuilder
import com.minnullin.models.PostType
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
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
                val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                val response = gson.toJson(repos.getAll())
                call.respond(HttpStatusCode.Accepted,response)
            }
        }
        route("/"){
            get{
                call.respond(HttpStatusCode.Accepted,"aaa")
            }
        }
    }

    //сборщик Json не используется
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
}

class PostDto(
    val id: Long,
    val authorName: String,
    val authorDrawable: Int,
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
        fun generateComp(model: Post) = PostDto(
            id = model.id,
            authorName = model.authorName,
            authorDrawable = model.authorDrawable,
            bodyText = model.bodyText,
            postDate = model.postDate,
            repostPost = model.repostPost,
            postType = model.postType,
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

