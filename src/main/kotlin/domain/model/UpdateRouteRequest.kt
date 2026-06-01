package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRouteRequest(
    val stateId: Int? = null,
    val name: String? = null,
    val originDir: String? = null,
    val originPlaceId: String? = null,
    val originLatitude: Double? = null,
    val originLongitude: Double? = null,
    val destinationDir: String? = null,
    val destinationPlaceId: String? = null,
    val destinationLatitude: Double? = null,
    val destinationLongitude: Double? = null
)
