package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Stop(
    val id: Int,
    val notice: String,
    val state: String,
    val recipient: String,
    val direction: String,
    val directionPlaceId: String?,
    val latitude: Double,
    val longitude: Double,
    val order: Int?,
    val note: String?
)
