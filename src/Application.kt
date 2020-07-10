package com.minnullin

import JWTTokenService
import PostRepository
import PostRepositoryBasic
import com.minnullin.models.Post
import com.minnullin.models.PostType
import com.minnullin.repos.UserRepository
import com.minnullin.repos.UserRepositoryBasic
import com.minnullin.routing.RoutingV1
import com.minnullin.service.FileService
import com.minnullin.service.PostService
import com.minnullin.service.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.with
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import javax.naming.ConfigurationException


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val repos = PostRepositoryBasic()




    install(KodeinFeature) {
        constant(tag = "upload-dir") with (environment.config.propertyOrNull("ktor.upload.dir")?.getString()
            ?: throw ConfigurationException("Upload dir is not specified"))
        bind<PostService>() with eagerSingleton { PostService(instance()) }
        bind<FileService>() with eagerSingleton { FileService(instance(tag = "upload-dir")) }
        bind<PostRepository>() with eagerSingleton { PostRepositoryBasic() }
        bind<PasswordEncoder>() with eagerSingleton { BCryptPasswordEncoder() }
        bind<JWTTokenService>() with eagerSingleton { JWTTokenService() }
        bind<UserRepository>() with eagerSingleton { UserRepositoryBasic() }
        bind<UserService>() with eagerSingleton {
            UserService(instance(), instance(), instance()).apply {
                runBlocking {
                    this@apply.save("vasya", "password")
                }
            }
        }
        bind<RoutingV1>() with eagerSingleton {
            RoutingV1(
                instance(tag = "upload-dir"),
                instance(),
                instance(),
                instance()
            )
        }
    }

    install(Authentication) {
        jwt {
            val jwtService by kodein().instance<JWTTokenService>()
            verifier(jwtService.verifier)
            val userService by kodein().instance<UserService>()

            validate {
                val id = it.payload.getClaim("id").asInt()
                userService.getModelById(id)
            }
        }
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
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
    }

    install(Routing) {
        val routingV1 by kodein().instance<RoutingV1>()
        routingV1.setup(this)
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
}


