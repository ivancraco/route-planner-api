package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateStopRequest(
    val stateId: Int? = null,
    val noticeId: Int? = null,
    val recipientName: String? = null,
    val direction: String? = null,
    val directionPlaceId: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val order: Int? = null,
    val note: String? = null,
)