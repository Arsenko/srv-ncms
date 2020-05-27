package com.minnullin

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import ru.minnullin.Post
import ru.minnullin.PostType
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
var postlist=listOf(
        Post(
                "netlogy",
                2,
                "First post in our network!",
                Date(),
                null,
                PostType.Post,
                0,
                false,
                8,
                2,
                null,
                null,
                null
        ),
        Post(
                "etlogy",
                2,
                "Second post in our network!",
                Date(),
                null,
                PostType.Event,
                0,
                false,
                8,
                2,
                Pair(60.0, 85.0),
                null,
                null
        ),
        Post(
                "tlogy",
                2,
                "Third post in our network!",
                Date(),
                null,
                PostType.Video,
                0,
                false,
                8,
                2,
                null,
                "https://www.youtube.com/watch?v=lO5_E9aObE0",
                null
        ),
        Post(
                "logy",
                2,
                "Fourth post in our network!",
                Date(),
                null,
                PostType.Advertising,
                0,
                false,
                8,
                2,
                null,
                "https://l.netology.ru/marketing_director_guide?utm_source=vk&utm_medium=smm&utm_campaign=bim_md_oz_vk_smm_guide&utm_content=12052020",
                1
        )
)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing{
        get("/"){
            call.respondText(Gson().toJson(postlist),ContentType.Text.Plain)
        }
        post("/"){
            val fromFront=call.receive<String>()
            call.respondText("you send: $fromFront",ContentType.Text.Plain)
        }
    }
}

