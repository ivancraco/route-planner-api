package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object RouteTable: UUIDTable(name = "Ruta", columnName = "id") {
    val userId = reference("idUsuario", UserTable)
    val stateId = reference("idEstado", RouteStateTable)
    val name = varchar("nombre", 50)
    val createdAt = timestamp("fecha")
    val originDir = varchar("direccionOrigen", 200)
    val originPlaceId = varchar("direccionOrigenPID", 255).nullable()
    val originLatitude = decimal("latitudOrigen", 10, 8)
    val originLongitude = decimal("longitudOrigen", 11, 8)
    val destinationDir = varchar("direccionDestino", 200)
    val destinationPlaceId = varchar("direccionDestinoPID", 255).nullable()
    val destinationLatitude = decimal("latitudDestino", 10, 8)
    val destinationLongitude = decimal("longitudDestino", 11, 8)
}