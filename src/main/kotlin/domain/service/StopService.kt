package com.routeplanner.api.domain.service

import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.UpdateStopRequest

interface StopService {
    suspend fun getAllByRoute(routeId: Int, userId: Int): List<Stop>?
    suspend fun getById(stopId: Int, userId: Int): Stop?
    suspend fun create(routeId: Int, userId: Int, request: CreateStopRequest): Stop?
    suspend fun update(stopId: Int, userId: Int, request: UpdateStopRequest): Stop?
    suspend fun delete(stopId: Int, userId: Int): Boolean
}