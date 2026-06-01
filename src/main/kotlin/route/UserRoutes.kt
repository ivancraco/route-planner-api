package com.routeplanner.api.route

import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.UpdateRouteRequest
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.model.failure
import com.routeplanner.api.domain.model.success
import com.routeplanner.api.domain.service.RouteService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.userRoutes(routeService: RouteService) {
    routing {
        authenticate("auth-jwt") {
            route("/routes") {
                get {
                    val user = call.authentication.principal<User>()
                    if (user == null) {
                        call.respond(
                            status = HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@get
                    }
                    val routes = routeService.getAllByUser(user.id)
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(routes)
                    )
                }

                // retorna todas las rutas (supervisor)
                get("/all") {
                    val routes = routeService.getAll()
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(routes)
                    )
                }

                get("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id inválido."
                            )
                        )
                        return@get
                    }
                    val route = routeService.getById(id)
                    if (route == null) {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "La ruta no existe."
                            )
                        )
                        return@get
                    }
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(route)
                    )
                }

                post {
                    val user = call.authentication.principal<User>()
                    if (user == null) {
                        call.respond(
                            status = HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@post
                    }
                    val request = call.receive<CreateRouteRequest>()
                    val route = routeService.create(user.id, request)
                    call.respond(
                        status = HttpStatusCode.Created,
                        success(route)
                    )
                }

                put("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id inválido."
                            )
                        )
                        return@put
                    }
                    val user = call.authentication.principal<User>()
                    if (user == null) {
                        call.respond(
                            status = HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@put
                    }
                    val request = call.receive<UpdateRouteRequest>()
                    val route = routeService.update(id, user.id, request)
                    if (route == null) {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "La ruta no existe o no pertenece al usuario."
                            )
                        )
                        return@put
                    }
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(route)
                    )
                }

                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id inválido."
                            )
                        )
                        return@delete
                    }
                    val user = call.authentication.principal<User>()
                    if (user == null) {
                        call.respond(
                            status = HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@delete
                    }
                    val deleted = routeService.delete(id, user.id)
                    if (!deleted) {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "La ruta no existe o no pertenece al usuario."
                            )
                        )
                        return@delete
                    }
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(mapOf("message" to "Ruta eliminada con éxito."))
                    )
                }
            }
        }
    }
}