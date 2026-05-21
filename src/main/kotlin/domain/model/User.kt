package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val fullName: String
)
