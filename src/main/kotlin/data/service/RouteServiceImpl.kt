package com.routeplanner.api.data.service

import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.UpdateRouteRequest
import com.routeplanner.api.domain.repository.RouteRepository
import com.routeplanner.api.domain.service.RouteService

class RouteServiceImpl(
    private val routeRepository: RouteRepository
) : RouteService {
    override suspend fun getAllByUser(userId: Int): List<Route> {
        return routeRepository.getAllByUser(userId)
    }

    override suspend fun getAll(): List<Route> {
        return routeRepository.getAll()
    }

    override suspend fun getById(routeId: Int): Route? {
        return routeRepository.getById(routeId)
    }

    override suspend fun create(
        userId: Int,
        request: CreateRouteRequest
    ): Route {
        return routeRepository.create(userId, request)
    }

    override suspend fun update(
        routeId: Int,
        userId: Int,
        request: UpdateRouteRequest
    ): Route? {
        return routeRepository.update(routeId, userId, request)
    }

    override suspend fun delete(routeId: Int, userId: Int): Boolean {
        return routeRepository.delete(routeId, userId)
    }
}