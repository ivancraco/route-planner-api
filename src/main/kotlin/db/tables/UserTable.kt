package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object UserTable: IntIdTable(name = "User", columnName = "user_id") {
    val username = varchar("username", 50).uniqueIndex()
    val fullName = varchar("full_name", 50)
    val password = varchar("password", 255)
    val isActive = bool("state")
}