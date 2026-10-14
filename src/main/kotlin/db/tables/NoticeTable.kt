package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object NoticeTable: IntIdTable(name = "Intimaciones_Caracter", columnName = "Cod") {
    val description = varchar("Descr", 50)
}