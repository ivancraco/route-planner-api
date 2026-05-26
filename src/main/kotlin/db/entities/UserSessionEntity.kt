package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.UserSessionTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class UserSessionEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserSessionEntity>(UserSessionTable)
    var userId by UserSessionTable.userId
    var token by UserSessionTable.token
    var createdAt by UserSessionTable.createdAt
    var expiresAt by UserSessionTable.expiresAt

    var user by UserEntity referencedOn UserSessionTable.userId
}