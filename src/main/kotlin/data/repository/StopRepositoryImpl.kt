package com.routeplanner.api.data.repository

import com.routeplanner.api.db.entities.RouteEntity
import com.routeplanner.api.db.entities.StopEntity
import com.routeplanner.api.db.tables.NoticeTable
import com.routeplanner.api.db.tables.RouteTable
import com.routeplanner.api.db.tables.StopTable
import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.UpdateStopRequest
import com.routeplanner.api.domain.repository.StopRepository
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.load
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class StopRepositoryImpl : StopRepository {
    override suspend fun getAllByRoute(routeId: Int, userId: Int): List<Stop>? {
        return suspendTransaction {
            if (!verifyRouteOwnership(routeId, userId)) return@suspendTransaction null
            StopEntity.find {
                StopTable.routeId eq routeId
            }
                .with(StopEntity::notice)
                .with(StopEntity::stopState)
                .orderBy(StopTable.order to SortOrder.ASC)
                .map { it.toStop() }
        }
    }

    override suspend fun getById(stopId: Int, userId: Int): Stop? {
        return suspendTransaction {
            val stopEntity = findStop(stopId)
                ?.load(StopEntity::notice, StopEntity::stopState)
                ?: return@suspendTransaction null
            if (!verifyRouteOwnership(stopEntity.routeId.value, userId))
                return@suspendTransaction null
            stopEntity.toStop()
        }
    }

    override suspend fun create(routeId: Int, userId: Int, request: CreateStopRequest): Stop? {
        return suspendTransaction {
            if (!verifyRouteOwnership(
                    routeId,
                    userId
                )
            ) return@suspendTransaction null
            StopEntity.new {
                this.routeId = EntityID(routeId, RouteTable)
                this.noticeId = EntityID(request.noticeId, NoticeTable)
                this.stopStateId = EntityID(1, StopTable)
                this.recipientName = request.recipientName
                this.direction = request.direction
                this.directionPlaceId = request.directionPlaceId
                this.latitude = request.latitude
                this.longitude = request.longitude
                this.order = request.order
                this.note = request.note
            }.toStop()
        }
    }

    override suspend fun update(
        stopId: Int,
        userId: Int,
        request: UpdateStopRequest
    ): Stop? {
        return suspendTransaction {
            val stopEntity = findStop(stopId) ?: return@suspendTransaction null
            if (!verifyRouteOwnership(
                    stopEntity.routeId.value,
                    userId
                )
            ) return@suspendTransaction null
            stopEntity.let {
                request.stateId?.let { stateId ->
                    it.stopStateId = EntityID(stateId, StopTable)
                }
                request.noticeId?.let { noticeId ->
                    it.noticeId = EntityID(noticeId, NoticeTable)
                }
                request.recipientName?.let { recipientName ->
                    it.recipientName = recipientName
                }
                request.direction?.let { direction ->
                    it.direction = direction
                }
                request.directionPlaceId?.let { directionPlaceId ->
                    it.directionPlaceId = directionPlaceId
                }
                request.latitude?.let { latitude ->
                    it.latitude = latitude
                }
                request.longitude?.let { longitude ->
                    it.longitude = longitude
                }
                request.order?.let { order ->
                    it.order = order
                }
                request.note?.let { note ->
                    it.note = note
                }
                it.toStop()
            }
        }
    }

    override suspend fun delete(
        stopId: Int,
        userId: Int
    ): Boolean {
        return suspendTransaction {
            val stopEntity = findStop(stopId) ?: return@suspendTransaction false
            if (!verifyRouteOwnership(
                    stopEntity.routeId.value,
                    userId
                )
            ) return@suspendTransaction false
            stopEntity.delete()
            true
        }
    }

    private fun findStop(stopId: Int): StopEntity? = StopEntity.findById(stopId)

    // Verifica que la ruta exista y pertenezca al usuario autenticado
    private fun verifyRouteOwnership(routeId: Int, userId: Int): Boolean {
        val route = RouteEntity.findById(routeId)
        return route?.userId?.value == userId
    }
}