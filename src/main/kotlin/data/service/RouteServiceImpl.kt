package com.routeplanner.api.data.service

import com.routeplanner.api.data.model.RouteEntityDto
import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.RouteSummary
import com.routeplanner.api.domain.model.UpdateRouteRequest
import com.routeplanner.api.domain.repository.RouteRepository
import com.routeplanner.api.domain.service.RouteService

class RouteServiceImpl(
    private val routeRepository: RouteRepository
) : RouteService {
    override suspend fun getAllByUser(userId: Int): List<RouteEntityDto> {
        return routeRepository.getAllByUser(userId)
    }

    override suspend fun getAll(): List<RouteSummary> {
        return routeRepository.getAll()
    }

    override suspend fun getByName(name: String): List<RouteSummary> {
        return routeRepository.getByUserName(name)
    }

    override suspend fun getById(routeId: String): Route? {
        return routeRepository.getById(routeId)
    }

    override suspend fun create(
        userId: Int,
        request: CreateRouteRequest
    ): Route {
        return routeRepository.create(userId, request)
    }

    override suspend fun update(
        routeId: String,
        userId: Int,
        request: UpdateRouteRequest
    ): Route? {
        return routeRepository.update(routeId, userId, request)
    }

    override suspend fun delete(routeId: String, userId: Int): Boolean {
        return routeRepository.delete(routeId, userId)
    }
}