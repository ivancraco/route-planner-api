package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StopSummary(
    val state: String,
    val direction: String,
    val order: Int? = null
)
