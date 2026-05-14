package com.routeplanner.api

import com.routeplanner.api.db.initDB
import com.routeplanner.api.di.configureKoin
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.jetbrains.exposed.v1.jdbc.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    initDB()

    routing {
        get("/") {
            call.respondText("Hola Mundo")
        }
    }
}