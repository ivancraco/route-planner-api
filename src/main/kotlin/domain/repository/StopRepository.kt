package com.routeplanner.api.domain.repository

import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.ReorderStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.StopState
import com.routeplanner.api.domain.model.UpdateStopRequest

interface StopRepository {
    suspend fun getStates(): List<StopState>
    suspend fun getAllByRoute(routeId: String, userId: Int): List<Stop>?
    suspend fun getById(stopId: String, userId: Int): Stop?
    suspend fun create(routeId: String, userId: Int, request: CreateStopRequest): Stop?
    suspend fun update(stopId: String, userId: Int, request: UpdateStopRequest): Stop?
    suspend fun delete(stopId: String, userId: Int): Boolean
    suspend fun reorder(routeId: String, userId: Int, request: List<ReorderStopRequest>): Boolean
}