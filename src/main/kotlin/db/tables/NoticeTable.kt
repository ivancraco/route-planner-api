package com.routeplanner.api.db.tables

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object NoticeTable: IntIdTable(name = "Notice", columnName = "notice_id") {
    val description = varchar("description", 50)
}