package com.minnullin

import PostRepository
import PostRepositoryBasic
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.minnullin.models.CounterChangeDto
import com.minnullin.models.PostType
import com.sun.xml.internal.ws.api.pipe.ContentType
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.ktor.KodeinFeature
import ru.minnullin.Post
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val repos = PostRepositoryBasic()
    val debugListOfPost = mutableListOf(
        Post(
            null,
            "netlogy",
            2,
            "First post in our network!",
            Date(),
            null,
            PostType.Post,
            2,
            false,
            0,
            false,
            8,
            2,
            null,
            null,
            null
        ),
        Post(
            null,
            "etlogy",
            2,
            "Second post in our network!",
            Date(),
            null,
            PostType.Event,
            3,
            true,
            0,
            false,
            8,
            2,
            Pair(60.0, 85.0),
            null,
            null
        ),
        Post(
            null,
            "tlogy",
            2,
            "Third post in our network!",
            Date(),
            null,
            PostType.Video,
            4,
            false,
            0,
            false,
            8,
            2,
            null,
            "https://www.youtube.com/watch?v=lO5_E9aObE0",
            null
        ),
        Post(
            null,
            "logy",
            2,
            "Fourth post in our network!",
            Date(),
            null,
            PostType.Advertising,
            0,
            false,
            0,
            false,
            8,
            2,
            null,
            "https://l.netology.ru/marketing_director_guide?utm_source=vk&utm_medium=smm&utm_campaign=bim_md_oz_vk_smm_guide&utm_content=12052020",
            1
        )
    )
    CoroutineScope(IO).launch {
        for (i in debugListOfPost) {
            repos.addPost(i)
        }
    }

    install(KodeinFeature) {
        bind<PostRepository>() with eagerSingleton { PostRepositoryBasic() }
    }

    install(StatusPages) {
        exception<NotFoundException> { e ->
            call.respond(HttpStatusCode.NotFound)
            throw e
        }
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest)
            throw e
        }
    }

    install(Routing) {
        route("/api/v1/posts/") {
            get {
                val respond = repos.getAll().map(PostDto.Companion::generateComp)
                call.respond(respond)
            }
        }

        route("/api/v1/posts/{id}") {
            get {
                val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException(
                    "id",
                    "Int"
                )
                val response = repos.getById(id)
                if (response != null) {
                    call.respond(response)
                }else{
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/api/v1/posts/{id}/delete") {
            get {
                val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException(
                    "id",
                    "Int"
                )
                val response = repos.deleteById(id)
                if (response) {
                    call.respond(HttpStatusCode.Accepted)
                }else{
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/api/v1/posts/changeCounter") {
            post {
                val receiveModel: CounterChangeDto = call.receive()
                receiveModel.let { outerIt ->
                    repos.changePostCounter(outerIt).let {
                        if (it != null) {
                            call.respond(it)
                        }else{
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }

                }
            }
        }

        route("/") {
            get {
                call.respond(HttpStatusCode.Accepted, "test completed")
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
        fun generateComp(model: Post) = model.id?.let {
            PostDto(
                id = it,
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
}

