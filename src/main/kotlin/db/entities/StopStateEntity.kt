package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.StopStateTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class StopStateEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<StopStateEntity>(StopStateTable)
    val description by StopStateTable.description
}