package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.timestamp

object UserSessionTable: IntIdTable(name = "UserSession", columnName = "session_id") {
    val userId = reference("user_id", UserTable)
    val token = varchar("token", 64)
    val createdAt = timestamp("created_at")
    val expiresAt = timestamp("expires_at")
}