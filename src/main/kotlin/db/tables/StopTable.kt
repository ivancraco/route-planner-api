package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object StopTable: IntIdTable(name = "Stop", columnName = "stop_id") {
    val routeId = reference("route_id", RouteTable)
    val noticeId = reference("notice_id", NoticeTable)
    val stopStateId = reference("stop_state_id", StopStateTable)
    val recipientName = varchar("recipient_name", 50)
    val direction = varchar("direction", 200)
    val directionPlaceId = varchar("direction_place_id", 255).nullable()
    val latitude = decimal("latitude", 10, 8)
    val longitude = decimal("longitude", 11, 8)
    val order = integer("order").nullable()
    val note = varchar("note", 255).nullable()
}