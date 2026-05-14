package com.routeplanner.api.db

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database


fun Application.initDB() {
    val url = environment.config.property("database.url").getString()
    val driver = environment.config.property("database.driver").getString()
    val user = environment.config.property("database.user").getString()
    val password = environment.config.property("database.password").getString()

    val db = Database.connect(
        url,
        driver,
        user = user,
        password = password
    )
}