package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int,
    val username: String,
    val isSupervisor: Boolean,
    val accessToken: String,
    val refreshToken: String
)
