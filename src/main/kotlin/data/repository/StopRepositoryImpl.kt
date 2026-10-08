package com.routeplanner.api.data.repository

import com.routeplanner.api.db.entities.RouteEntity
import com.routeplanner.api.db.entities.StopEntity
import com.routeplanner.api.db.entities.StopStateEntity
import com.routeplanner.api.db.tables.NoticeTable
import com.routeplanner.api.db.tables.RouteTable
import com.routeplanner.api.db.tables.StopStateTable
import com.routeplanner.api.db.tables.StopTable
import com.routeplanner.api.domain.model.CreateStopRequest
import com.routeplanner.api.domain.model.ReorderStopRequest
import com.routeplanner.api.domain.model.Stop
import com.routeplanner.api.domain.model.StopState
import com.routeplanner.api.domain.model.UpdateStopRequest
import com.routeplanner.api.domain.repository.StopRepository
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.load
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class StopRepositoryImpl : StopRepository {
    override suspend fun getStates(): List<StopState> {
        return suspendTransaction {
            StopStateEntity.all().map {
                StopState(
                    id = it.id.value,
                    description = it.description
                )
            }
        }
    }

    override suspend fun getAllByRoute(routeId: String, userId: Int): List<Stop>? {
        return suspendTransaction {
            if (!verifyRouteOwnership(routeId, userId)) return@suspendTransaction null
            StopEntity.find {
                StopTable.routeId eq UUID.fromString(routeId)
            }
                .with(StopEntity::notice)
                .with(StopEntity::stopState)
                .orderBy(StopTable.order to SortOrder.ASC)
                .map { it.toStop() }
        }
    }

    override suspend fun getById(stopId: String, userId: Int): Stop? {
        return suspendTransaction {
            val stopEntity = findStop(stopId)
                ?.load(StopEntity::notice, StopEntity::stopState)
                ?: return@suspendTransaction null
            if (!verifyRouteOwnership(stopEntity.routeId.value.toString(), userId))
                return@suspendTransaction null
            stopEntity.toStop()
        }
    }

    override suspend fun create(routeId: String, userId: Int, request: CreateStopRequest): Stop? {
        return suspendTransaction {
            if (!verifyRouteOwnership(
                    routeId,
                    userId
                )
            ) return@suspendTransaction null
            StopEntity.new(UUID.fromString(request.id)) {
                this.routeId = EntityID(UUID.fromString(routeId), RouteTable)
                this.noticeId = EntityID(request.noticeId, NoticeTable)
                this.stopStateId = EntityID(1, StopStateTable)
                this.recipientName = request.recipient
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
        stopId: String,
        userId: Int,
        request: UpdateStopRequest
    ): Stop? {
        return suspendTransaction {
            val stopEntity = findStop(stopId) ?: return@suspendTransaction null
            if (!verifyRouteOwnership(
                    stopEntity.routeId.value.toString(),
                    userId
                )
            ) return@suspendTransaction null
            stopEntity.let {
                request.stateId?.let { stateId ->
                    it.stopStateId = EntityID(stateId, StopStateTable)
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
        stopId: String,
        userId: Int
    ): Boolean {
        return suspendTransaction {
            val stopEntity = findStop(stopId) ?: return@suspendTransaction false
            if (!verifyRouteOwnership(
                    stopEntity.routeId.value.toString(),
                    userId
                )
            ) return@suspendTransaction false
            stopEntity.delete()
            true
        }
    }

    override suspend fun reorder(
        routeId: String,
        userId: Int,
        request: List<ReorderStopRequest>
    ): Boolean {
        return suspendTransaction {
            if (!verifyRouteOwnership(routeId, userId)) return@suspendTransaction false

            request.forEach { reorderRequest ->
                val stop = findStop(reorderRequest.id) ?: return@suspendTransaction false
                // verificar que la parada pertenece a la ruta
                if (stop.routeId.value.toString() != routeId) return@suspendTransaction false
                stop.order = reorderRequest.order
            }
            true
        }
    }

    private fun findStop(stopId: String): StopEntity? = StopEntity.findById(UUID.fromString(stopId))

    // Verifica que la ruta exista y pertenezca al usuario autenticado
    private fun verifyRouteOwnership(routeId: String, userId: Int): Boolean {
        val route = RouteEntity.findById(UUID.fromString(routeId))
        return route?.userId?.value == userId
    }
}