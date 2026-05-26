package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ApiError? = null
)

@Serializable
data class ApiError(
    val code: Int,
    val message: String
)

fun <T> success(data: T) = ApiResponse(
    success = true,
    data = data
)

fun failure(code: Int, message: String) = ApiResponse<Nothing>(
    success = false,
    error = ApiError(code = code, message = message)
)