package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class CreateRouteRequest(
    val name: String,
    val createdAt: Instant,
    val originDir: String,
    val originPlaceId: String? = null,
    val originLatitude: Double,
    val originLongitude: Double,
    val destinationDir: String,
    val destinationPlaceId: String? = null,
    val destinationLatitude: Double,
    val destinationLongitude: Double
)
