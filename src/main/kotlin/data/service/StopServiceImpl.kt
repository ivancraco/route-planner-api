package com.routeplanner.api.data.service

import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.ReorderStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.StopState
import com.routeplanner.api.domain.model.UpdateStopRequest
import com.routeplanner.api.domain.repository.StopRepository
import com.routeplanner.api.domain.service.StopService

class StopServiceImpl(val stopRepository: StopRepository) : StopService {
    override suspend fun getStates(): List<StopState> {
        return stopRepository.getStates()
    }

    override suspend fun getAllByRoute(
        routeId: String,
        userId: Int
    ): List<Stop>? {
        return stopRepository.getAllByRoute(routeId, userId)
    }

    override suspend fun getById(
        stopId: String,
        userId: Int
    ): Stop? {
        return stopRepository.getById(stopId, userId)
    }

    override suspend fun create(
        routeId: String,
        userId: Int,
        request: CreateStopRequest
    ): Stop? {
        return stopRepository.create(routeId, userId, request)
    }

    override suspend fun update(
        stopId: String,
        userId: Int,
        request: UpdateStopRequest
    ): Stop? {
        return stopRepository.update(stopId, userId, request)
    }

    override suspend fun delete(stopId: String, userId: Int): Boolean {
        return stopRepository.delete(stopId, userId)
    }

    override suspend fun reorder(routeId: String, userId: Int, request: List<ReorderStopRequest>): Boolean {
        return stopRepository.reorder(routeId, userId, request)
    }
}