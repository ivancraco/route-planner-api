package com.routeplanner.api.data.service

import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.UpdateStopRequest
import com.routeplanner.api.domain.repository.StopRepository
import com.routeplanner.api.domain.service.StopService

class StopServiceImpl(val stopRepository: StopRepository) : StopService {
    override suspend fun getAllByRoute(
        routeId: Int,
        userId: Int
    ): List<Stop>? {
        return stopRepository.getAllByRoute(routeId, userId)
    }

    override suspend fun getById(
        stopId: Int,
        userId: Int
    ): Stop? {
        return stopRepository.getById(stopId, userId)
    }

    override suspend fun create(
        routeId: Int,
        userId: Int,
        request: CreateStopRequest
    ): Stop? {
        return stopRepository.create(routeId, userId, request)
    }

    override suspend fun update(
        stopId: Int,
        userId: Int,
        request: UpdateStopRequest
    ): Stop? {
        return stopRepository.update(stopId, userId, request)
    }

    override suspend fun delete(stopId: Int, userId: Int): Boolean {
        return stopRepository.delete(stopId, userId)
    }

}