package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Route(
    val id: Int,
    val name: String,
    val state: String,
    val createdAt: Instant,
    val originDir: String,
    val originPlaceId: String?,
    val originLatitude: Double,
    val originLongitude: Double,
    val destinationDir: String,
    val destinationPlaceId: String?,
    val destinationLatitude: Double,
    val destinationLongitude: Double,
    val stops: List<Stop>
)

