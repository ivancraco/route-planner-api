package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class RouteSummary(
    val name: String,
    val user: String,
    val date: Instant,
    val origin: String,
    val destination: String,
    val state: String,
    val stops: List<StopSummary>
)
