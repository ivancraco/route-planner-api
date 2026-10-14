package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object UserTable: IntIdTable(name = "Usuarios", columnName = "UsuCodigo") {
    val username = varchar("UsuNombre", 20)
    val fullName = varchar("ApeNom", 50)
    val password = char("clave", 32)
    val isActive = char("Estado", 2)
}