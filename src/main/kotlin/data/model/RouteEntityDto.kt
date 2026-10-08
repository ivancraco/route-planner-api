package com.routeplanner.api.data.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Clase para devolvérsela al usuario individual, no supervisor.
 * Cuando hace login en otro dispositivo o logout -> login
 * **/
@Serializable
data class RouteEntityDto(
    val id: String,
    val stateId: Int,
    val name: String,
    val createdAt: Instant,
    val originDir: String,
    val originPlaceId: String?,
    val originLatitude: Double,
    val originLongitude: Double,
    val destinationDir: String,
    val destinationPlaceId: String?,
    val destinationLatitude: Double,
    val destinationLongitude: Double,
    val stops: List<StopEntityDto>
)
