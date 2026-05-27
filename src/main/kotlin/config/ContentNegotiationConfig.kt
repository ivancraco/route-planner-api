package com.routeplanner.api.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

/**
 * Configura la negociación de contenido (Content Negotiation) de la aplicación Ktor.
 *
 * Esta configuración habilita el soporte para JSON usando kotlinx.serialization,
 * permitiendo la conversión automática entre objetos Kotlin y JSON en peticiones
 * y respuestas HTTP.
 *
 * La configuración actual:
 * - Usa JSON como formato principal de intercambio de datos
 * - Habilita `prettyPrint` para mejorar la legibilidad del JSON en desarrollo
 *
 * Esto permite que los endpoints de la API trabajen directamente con objetos Kotlin
 * sin necesidad de serialización manual.
 */
fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
}