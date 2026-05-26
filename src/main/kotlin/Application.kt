package com.routeplanner.api

import com.routeplanner.api.config.configureContentNegotiation
import com.routeplanner.api.config.configureSecurity
import com.routeplanner.api.db.initDB
import com.routeplanner.api.di.configureKoin
import com.routeplanner.api.domain.service.UserService
import com.routeplanner.api.route.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    initDB()
    configureContentNegotiation()

    val userService = get<UserService>()
    configureSecurity(userService)
    userRoutes(userService)

    routing {
        get("/") {
            call.respondText("Hola Mundo")
        }
    }
}