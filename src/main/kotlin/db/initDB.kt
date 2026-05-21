package com.routeplanner.api.db

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database


fun Application.initDB() {
    val dotenv = dotenv()

    val dbUrl =
        System.getenv("DATABASE_URL")
            ?: dotenv["DATABASE_URL"]
            ?: error("DATABASE_URL faltante")

    val dbDriver =
        System.getenv("DATABASE_DRIVER")
            ?: dotenv["DATABASE_DRIVER"]
            ?: error("DATABASE_DRIVER faltante")

    val dbUser =
        System.getenv("DATABASE_USER")
            ?: dotenv["DATABASE_USER"]
            ?: error("DATABASE_USER faltante")

    val dbPass =
        System.getenv("DATABASE_PASSWORD")
            ?: dotenv["DATABASE_PASSWORD"]
            ?: error("DATABASE_PASSWORD faltante")

    Database.connect(
        url = dbUrl,
        driver = dbDriver,
        user = dbUser,
        password = dbPass
    )
}