package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object SupervisorTable: IdTable<Int>(name = "Supervisor") {
    override val id = reference("user_id", UserTable)
    override val primaryKey = PrimaryKey(id)
}
