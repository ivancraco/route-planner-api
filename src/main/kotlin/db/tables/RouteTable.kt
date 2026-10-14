package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object RouteTable: UUIDTable(name = "Ruta", columnName = "id") {
    val userId = reference("idUsuario", UserTable)
    val stateId = reference("idEstado", RouteStateTable)
    val name = varchar("nombre", 50)
    val createdAt = timestamp("fecha")
    val originDir = varchar("dirOrigen", 200)
    val originPlaceId = varchar("dirOrigenPlaceId", 255).nullable()
    val originLatitude = decimal("origenLatitud", 10, 8)
    val originLongitude = decimal("origenLongitud", 11, 8)
    val destinationDir = varchar("dirDestino", 200)
    val destinationPlaceId = varchar("dirDestinoPlaceId", 255).nullable()
    val destinationLatitude = decimal("destinoLatitud", 10, 8)
    val destinationLongitude = decimal("destinoLongitud", 11, 8)
}