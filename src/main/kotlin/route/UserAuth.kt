package com.routeplanner.api.route

import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.RefreshRequest
import com.routeplanner.api.domain.model.failure
import com.routeplanner.api.domain.model.success
import com.routeplanner.api.domain.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.userAuth(userService: UserService) {
    routing {
        route("/users") {

            post("/login") {
                val request = call.receive<LoginRequest>()
                val loginResponse = userService.login(request)
                if (loginResponse == null) {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        failure(
                            code = HttpStatusCode.NotFound.value,
                            "Los datos de usuario y/o contraseña son incorrectos."
                        )
                    )
                    return@post
                }
                call.respond(
                    status = HttpStatusCode.OK,
                    success(loginResponse)
                )
            }

            post("/refresh") {
                val request = call.receive<RefreshRequest>()
                val loginResponse = userService.updateTokens(request.refreshToken)
                if (loginResponse == null) {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        failure(
                            code = HttpStatusCode.Unauthorized.value,
                            "Token de actualización inválido o expirado."
                        )
                    )
                    return@post
                }
                call.respond(
                    status = HttpStatusCode.OK,
                    success(loginResponse)
                )
            }

            authenticate("auth-jwt") {
                get {
                    val users = userService.getAllUsers()
                    if (users.isEmpty()) {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            failure(
                                code = HttpStatusCode.NotFound.value,
                                "No se encontraron usuarios."
                            )
                        )
                        return@get
                    }
                    call.respond(success(users))
                }

                get("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            failure(code = HttpStatusCode.BadRequest.value, "Id inválido.")
                        )
                        return@get
                    }
                    val user = userService.getUserById(id)
                    if (user == null) {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            failure(
                                code = HttpStatusCode.NotFound.value,
                                "Usuario no encontrado."
                            )
                        )
                        return@get
                    }
                    call.respond(success(user))
                }

                post("/logout") {
                    val request = call.receive<RefreshRequest>()
                    userService.logout(request.refreshToken)
                    // Eliminar en cliente el access token.
                    call.respond(
                        status = HttpStatusCode.OK,
                        success(mapOf("message" to "Sesión cerrada con éxito."))
                    )
                }
            }
        }
    }
}