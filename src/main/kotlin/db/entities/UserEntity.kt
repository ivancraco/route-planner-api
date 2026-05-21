package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.UserTable
import com.routeplanner.api.domain.model.User
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UserTable)
    var username by UserTable.username
    var fullName by UserTable.fullName
    var password by UserTable.password
    var isActive by UserTable.isActive

    fun toUser() = User(
        id = id.value,
        username = username,
        fullName = fullName
    )
}