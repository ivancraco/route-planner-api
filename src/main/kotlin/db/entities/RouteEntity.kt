package com.routeplanner.api.db.entities

import com.routeplanner.api.data.model.RouteEntityDto
import com.routeplanner.api.db.tables.RouteTable
import com.routeplanner.api.db.tables.StopTable
import com.routeplanner.api.domain.model.Route
import com.routeplanner.api.domain.model.RouteSummary
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.math.BigDecimal
import java.util.UUID

class RouteEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RouteEntity>(RouteTable)
    var userId by RouteTable.userId
    var stateId by RouteTable.stateId
    var name by RouteTable.name
    var createdAt by RouteTable.createdAt
    var originDir by RouteTable.originDir
    var originPlaceId by RouteTable.originPlaceId
    private var _originLatitude by RouteTable.originLatitude
    var originLatitude: Double
        get() = _originLatitude.toDouble()
        set(v) {
            _originLatitude = BigDecimal.valueOf(v)
        }
    private var _originLongitude by RouteTable.originLongitude
    var originLongitude: Double
        get() = _originLongitude.toDouble()
        set(v) {
            _originLongitude = BigDecimal.valueOf(v)
        }
    var destinationDir by RouteTable.destinationDir
    var destinationPlaceId by RouteTable.destinationPlaceId
    private var _destinationLatitude by RouteTable.destinationLatitude
    var destinationLatitude: Double
        get() = _destinationLatitude.toDouble()
        set(v) {
            _destinationLatitude = BigDecimal.valueOf(v)
        }
    private var _destinationLongitude by RouteTable.destinationLongitude
    var destinationLongitude: Double
        get() = _destinationLongitude.toDouble()
        set(v) {
            _destinationLongitude = BigDecimal.valueOf(v)
        }
    val user by UserEntity referencedOn RouteTable.userId
    val state by RouteStateEntity referencedOn RouteTable.stateId
    val stops by StopEntity referrersOn StopTable.routeId

    fun toRoute() = Route(
        id = id.value.toString(),
        name = name,
        state = state.description,
        createdAt = createdAt,
        originDir = originDir,
        originPlaceId = originPlaceId,
        originLatitude = originLatitude,
        originLongitude = originLongitude,
        destinationDir = destinationDir,
        destinationPlaceId = destinationPlaceId,
        destinationLatitude = destinationLatitude,
        destinationLongitude = destinationLongitude,
        stops = stops.map { it.toStop() }
    )

    fun toRouteSummary() = RouteSummary(
        name = name,
        user = user.username,
        date = createdAt,
        origin = originDir,
        destination = destinationDir,
        state = state.description,
        stops = stops.map { it.toStopSummary() }
    )

    fun toRouteDto() = RouteEntityDto(
        id = id.value.toString(),
        name = name,
        stateId = stateId.value,
        createdAt = createdAt,
        originDir = originDir,
        originPlaceId = originPlaceId,
        originLatitude = originLatitude,
        originLongitude = originLongitude,
        destinationDir = destinationDir,
        destinationPlaceId = destinationPlaceId,
        destinationLatitude = destinationLatitude,
        destinationLongitude = destinationLongitude,
        stops = stops.map { it.toStopDto() }
    )
}