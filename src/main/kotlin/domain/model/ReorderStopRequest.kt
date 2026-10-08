package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReorderStopRequest(
    val id: String,
    val order: Int
)
