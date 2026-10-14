package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object RouteStateTable: IntIdTable(name = "Ruta_Estado", columnName = "Cod") {
    val description = varchar("Descr", 50)
}