package com.routeplanner.api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PageResult<T>(
    val items: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int
)