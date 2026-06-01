package com.routeplanner.api.route

import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.UpdateStopRequest
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.model.failure
import com.routeplanner.api.domain.model.success
import com.routeplanner.api.domain.service.StopService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.userStops(stopService: StopService) {
    routing {
        authenticate("auth-jwt") {
            route("/routes/{routeId}/stops") {
                get {
                    val user = call.principal<User>()
                    if (user == null) {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@get
                    }
                    val routeId = call.parameters["routeId"]?.toIntOrNull()
                    if (routeId == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id de ruta inválido."
                            )
                        )
                        return@get
                    }
                    val stops = stopService.getAllByRoute(routeId, user.id)
                    if (stops == null) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "La ruta asociada a este punto de entrega no pertenece al usuario."
                            )
                        )
                        return@get
                    }
                    call.respond(
                        HttpStatusCode.OK,
                        success(stops)
                    )
                }
                post {
                    val user = call.principal<User>()
                    if (user == null) {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@post
                    }
                    val routeId = call.parameters["routeId"]?.toIntOrNull()
                    if (routeId == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id de ruta inválido."
                            )
                        )
                        return@post
                    }
                    val request = call.receive<CreateStopRequest>()
                    val stop = stopService.create(routeId, user.id, request)
                    if (stop == null) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "La ruta asociada a este punto de entrega no pertenece al usuario."
                            )
                        )
                        return@post
                    }
                    call.respond(
                        HttpStatusCode.Created,
                        success(stop)
                    )
                }
            }
            route("/stops") {
                get("/{id}") {
                    val user = call.principal<User>()
                    if (user == null) {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@get
                    }
                    val stopId = call.parameters["id"]?.toIntOrNull()
                    if (stopId == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id de punto de entrega inválido."
                            )
                        )
                        return@get
                    }
                    val stop = stopService.getById(stopId, user.id)
                    if (stop == null) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "Punto de entrega no encontrado o la ruta asociada a este punto de entrega no pertenece al usuario."
                            )
                        )
                        return@get
                    }
                    call.respond(
                        HttpStatusCode.OK,
                        success(stop)
                    )
                }
                put("/{id}") {
                    val user = call.principal<User>()
                    if (user == null) {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@put
                    }
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id de punto de entrega inválido."
                            )
                        )
                        return@put
                    }
                    val request = call.receive<UpdateStopRequest>()
                    val stop = stopService.update(id, user.id, request)
                    if (stop == null) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "Punto de entrega no encontrado o la ruta asociada a este punto de entrega no pertenece al usuario."
                            )
                        )
                        return@put
                    }
                    call.respond(
                        HttpStatusCode.OK,
                        success(stop)
                    )
                }
                delete("/{id}") {
                    val user = call.principal<User>()
                    if (user == null) {
                        call.respond(
                            HttpStatusCode.Unauthorized,
                            failure(
                                HttpStatusCode.Unauthorized.value,
                                "Usuario no autenticado."
                            )
                        )
                        return@delete
                    }
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            failure(
                                HttpStatusCode.BadRequest.value,
                                "Id de punto de entrega inválido."
                            )
                        )
                        return@delete
                    }
                    val deleted = stopService.delete(id, user.id)
                    if (!deleted) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            failure(
                                HttpStatusCode.NotFound.value,
                                "Punto de entrega no encontrado o la ruta asociada a este punto de entrega no pertenece al usuario."
                            )
                        )
                        return@delete
                    }
                    call.respond(
                        HttpStatusCode.OK,
                        success(
                            mapOf("message" to "Punto de entrega eliminado con éxito.")
                        )
                    )
                }
            }
        }
    }
}