package com.routeplanner.api.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StopEntityDto(
    val id: String,
    val noticeId: Int,
    val stateId: Int,
    val recipient: String,
    val direction: String,
    val directionPlaceId: String?,
    val latitude: Double,
    val longitude: Double,
    val order: Int,
    val note: String?
)
