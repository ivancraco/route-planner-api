package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object StopTable: UUIDTable(name = "Entrega", columnName = "id") {
    val routeId = reference("idRuta", RouteTable)
    val noticeId = reference("idIntimacionesCaracter", NoticeTable)
    val stopStateId = reference("idIntimacionesResultados", StopStateTable)
    val recipientName = varchar("destinatario", 50)
    val direction = varchar("direcccion", 200)
    val directionPlaceId = varchar("direccionPID", 255).nullable()
    val directionDetail = varchar("detalleDomicilio", 30).nullable()
    val latitude = decimal("latitud", 10, 8)
    val longitude = decimal("longitud", 11, 8)
    val order = integer("orden").nullable()
    val note = varchar("nota", 255).nullable()
}