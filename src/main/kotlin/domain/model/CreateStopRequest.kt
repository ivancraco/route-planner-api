package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateStopRequest(
    val id: String,
    val noticeId: Int,
    val stateId: Int,
    val recipient: String,
    val direction: String,
    val directionDetail: String? = null,
    val directionPlaceId: String? = null,
    val latitude: Double,
    val longitude: Double,
    val order: Int,
    val note: String? = null,
)
