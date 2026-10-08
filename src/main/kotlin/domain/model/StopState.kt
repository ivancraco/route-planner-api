package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StopState(
    val id: Int,
    val description: String
)
