package com.routeplanner.api.domain.service

import com.routeplanner.api.data.model.RouteEntityDto
import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.RouteSummary
import com.routeplanner.api.domain.model.UpdateRouteRequest

interface RouteService {
    suspend fun getAllByUser(userId: Int): List<RouteEntityDto>
    suspend fun getAll(): List<RouteSummary>
    suspend fun getByName(name: String): List<RouteSummary>
    suspend fun getById(routeId: String): Route?
    suspend fun create(userId: Int, request: CreateRouteRequest): Route
    suspend fun update(routeId: String, userId: Int, request: UpdateRouteRequest): Route?
    suspend fun delete(routeId: String, userId: Int): Boolean
}