package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.SupervisorTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class SupervisorEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SupervisorEntity>(SupervisorTable)
    var user by UserEntity referencedOn SupervisorTable.id
}