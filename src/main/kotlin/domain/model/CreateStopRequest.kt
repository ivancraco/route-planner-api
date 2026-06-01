package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateStopRequest(
    val noticeId: Int,
    val recipientName: String,
    val direction: String,
    val directionPlaceId: String? = null,
    val latitude: Double,
    val longitude: Double,
    val order: Int? = null,
    val note: String? = null,
)
