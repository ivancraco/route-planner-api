package com.routeplanner.api.data.repository

import com.routeplanner.api.data.model.RouteEntityDto
import com.routeplanner.api.db.entities.RouteEntity
import com.routeplanner.api.db.entities.UserEntity
import com.routeplanner.api.db.tables.RouteStateTable
import com.routeplanner.api.db.tables.RouteTable
import com.routeplanner.api.db.tables.UserTable
import com.routeplanner.api.domain.model.CreateRouteRequest
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.RouteSummary
import com.routeplanner.api.domain.model.UpdateRouteRequest
import com.routeplanner.api.domain.repository.RouteRepository
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inSubQuery
import org.jetbrains.exposed.v1.dao.load
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.select
import java.util.UUID

class RouteRepositoryImpl : RouteRepository {
    override suspend fun getAllByUser(userId: Int): List<RouteEntityDto> {
        return suspendTransaction {
            RouteEntity.find {
                RouteTable.userId eq userId
            }
                .with(
                    RouteEntity::state,
                    RouteEntity::stops
                )
                .map {
                    it.toRouteDto()
                }
        }
    }

    override suspend fun getAll(): List<RouteSummary> {
        return suspendTransaction {
            RouteEntity.all()
                .with(
                    RouteEntity::user,
                    RouteEntity::state,
                    RouteEntity::stops
                )
                .map {
                    it.toRouteSummary()
                }
        }
    }

    override suspend fun getByUserName(userName: String): List<RouteSummary> {
        return suspendTransaction {
            RouteEntity.find {
                RouteTable.userId inSubQuery(
                        UserTable
                            .select(UserTable.id)
                            .where(UserTable.username eq userName)
                )
            }
                .with(
                    RouteEntity::user,
                    RouteEntity::state,
                    RouteEntity::stops
                )
                .map {
                    it.toRouteSummary()
                }
        }
    }

    override suspend fun getById(routeId: String): Route? {
        return suspendTransaction {
            RouteEntity.findById(UUID.fromString(routeId))
                ?.load(
                    RouteEntity::state,
                    RouteEntity::stops
                )
                ?.toRoute()
        }
    }

    override suspend fun create(
        userId: Int,
        request: CreateRouteRequest
    ): Route {
        return suspendTransaction {
            RouteEntity.new(UUID.fromString(request.id)) {
                this.userId = EntityID(userId, UserTable)
                this.stateId = EntityID(request.stateId, RouteStateTable)
                this.name = request.name
                this.createdAt = request.createdAt
                this.originDir = request.originDir
                this.originPlaceId = request.originPlaceId
                this.originLatitude = request.originLatitude
                this.originLongitude = request.originLongitude
                this.destinationDir = request.destinationDir
                this.destinationPlaceId = request.destinationPlaceId
                this.destinationLatitude = request.destinationLatitude
                this.destinationLongitude = request.destinationLongitude
            }.toRoute()
        }
    }

    override suspend fun update(
        routeId: String,
        userId: Int,
        request: UpdateRouteRequest
    ): Route? {
        return suspendTransaction {
            val routeEntity = RouteEntity.findById(UUID.fromString(routeId))
            routeEntity?.let {
                if (it.userId.value != userId) return@suspendTransaction null
                request.stateId?.let { stateId ->
                    it.stateId = EntityID(stateId, RouteStateTable)
                }
                request.name?.let { name ->
                    it.name = name
                }
                request.originDir?.let { originDir ->
                    it.originDir = originDir
                }
                request.originPlaceId?.let { originPlaceId ->
                    it.originPlaceId = originPlaceId
                }
                request.originLatitude?.let { originLatitude ->
                    it.originLatitude = originLatitude
                }
                request.originLongitude?.let { originLongitude ->
                    it.originLongitude = originLongitude
                }
                request.destinationDir?.let { destinationDir ->
                    it.destinationDir = destinationDir
                }
                request.destinationPlaceId?.let { destinationPlaceId ->
                    it.destinationPlaceId = destinationPlaceId
                }
                request.destinationLatitude?.let { destinationLatitude ->
                    it.destinationLatitude = destinationLatitude
                }
                request.destinationLongitude?.let { destinationLongitude ->
                    it.destinationLongitude = destinationLongitude
                }
                it.toRoute()
            }
        }
    }

    override suspend fun delete(routeId: String, userId: Int): Boolean {
        return suspendTransaction {
            val route = RouteEntity.findById(UUID.fromString(routeId))
            if (route?.userId?.value != userId) return@suspendTransaction false
            route.delete()
            true
        }
    }
}