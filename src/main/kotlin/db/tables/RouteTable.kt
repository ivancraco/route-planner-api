package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.timestamp

object RouteTable: IntIdTable(name = "Route", columnName = "route_id") {
    val userId = reference("user_id", UserTable)
    val stateId = reference("route_state_id", RouteStateTable)
    val name = varchar("name", 50)
    val createdAt = timestamp("created_at")
    val originDir = varchar("origin_dir", 200)
    val originPlaceId = varchar("origin_place_id", 255).nullable()
    val originLatitude = decimal("origin_latitude", 10, 8)
    val originLongitude = decimal("origin_longitude", 11, 8)
    val destinationDir = varchar("destination_dir", 200)
    val destinationPlaceId = varchar("destination_place_id", 255).nullable()
    val destinationLatitude = decimal("destination_latitude", 10, 8)
    val destinationLongitude = decimal("destination_longitude", 11, 8)
}