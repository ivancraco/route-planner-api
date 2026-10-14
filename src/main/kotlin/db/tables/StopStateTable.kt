package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object StopStateTable: IntIdTable(name = "Intimaciones_Resultados", columnName = "Cod") {
    val description = varchar("Descr", 50)
}