package com.routeplanner.api.domain.service

import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.UpdateRouteRequest

interface RouteService {
    suspend fun getAllByUser(userId: Int): List<Route>
    suspend fun getAll(): List<Route>
    suspend fun getById(routeId: Int): Route?
    suspend fun create(userId: Int, request: CreateRouteRequest): Route
    suspend fun update(routeId: Int, userId: Int, request: UpdateRouteRequest): Route?
    suspend fun delete(routeId: Int, userId: Int): Boolean
}