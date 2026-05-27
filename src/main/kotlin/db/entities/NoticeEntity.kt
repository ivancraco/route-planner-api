package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.NoticeTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class NoticeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<NoticeEntity>(NoticeTable)
    val description by NoticeTable.description
}