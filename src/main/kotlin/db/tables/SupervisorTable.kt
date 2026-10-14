package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IdTable

object SupervisorTable: IdTable<Int>(name = "Ruta_Supervisor") {
    override val id = reference("UsuCodigo", UserTable)
    override val primaryKey = PrimaryKey(id)
}
