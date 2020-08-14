package com.minnullin.routing

import com.minnullin.models.*
import com.minnullin.service.FileService
import com.minnullin.service.PostService
import com.minnullin.service.UserService
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import jdk.nashorn.internal.runtime.regexp.joni.Config.log

class RoutingV1(private val staticPath: String,
                private val postService: PostService,
                private val fileService: FileService,
                private val userService: UserService
) {
    fun setup(configuration: Routing){
        with(configuration){
            route("/api/v1/") {
                post("/registration") {
                    val input=call.receive<AuthenticationInDto>()
                    val response=userService.registration(input)
                    if(response!=null){
                        call.respond(response)
                    }else{
                        call.respond("username already exist")
                    }
                }

                post("/authentication") {
                    val input = call.receive<AuthenticationInDto>()
                    val response = userService.authenticate(input)
                    call.respond(response)
                }
            }
       //     authenticate {
                route("/api/v1/") {
                    static("/static") {
                        files(staticPath)
                    }
                }
                route("/api/v1/posts/") {
                    get {
                        val respond = postService.getAll(call.authentication.principal<User>()!!.name)
                        call.respond(respond)
                    }
                }
                route("/api/v1/posts/") {
                    post {
                        val input = call.receive<PostDtoFinal>()
                        val response = postService.addPost(input)
                        call.respond(HttpStatusCode.Accepted)
                    }
                }

                route("/api/v1/posts/{id}") {
                    get {
                        val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException(
                            "id",
                            "Int"
                        )
                        val response = postService.getById(id)
                        if (response != null) {
                            call.respond(response)
                        } else {
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
                        call.respond(postService.deleteById(id, call.authentication.principal<User>()!!.name))
                    }
                }

                route("/api/v1/posts/changeCounter") {
                    post {
                        val receiveModel: CounterChangeDto = call.receive()
                        receiveModel.let { outerIt ->
                            postService.changeCounter(outerIt,call.authentication.principal<User>()!!.name).let {
                                if (it != null) {
                                    call.respond(it)
                                } else {
                                    call.respond(HttpStatusCode.BadRequest)
                                }
                            }

                        }
                    }
   //             }
                route("/media") {
                    post {
                        val multipart = call.receiveMultipart()
                        val response = fileService.save(multipart)
                        call.respond(response)
                    }
                }
            }
            route("/") {
                get {
                    call.respond(HttpStatusCode.Accepted, "test completed")
                }
            }
        }
    }
}