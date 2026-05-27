package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.RouteStateTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class RouteStateEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RouteStateEntity>(RouteStateTable)
    val description by RouteStateTable.description
}