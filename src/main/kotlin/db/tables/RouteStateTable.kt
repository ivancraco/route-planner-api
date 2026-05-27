package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object RouteStateTable: IntIdTable(name = "RouteState", columnName = "route_state_id") {
    val description = varchar("description", 50)
}