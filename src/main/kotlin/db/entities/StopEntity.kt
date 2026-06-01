package com.routeplanner.api.db.entities

import com.routeplanner.api.db.tables.StopTable
import com.routeplanner.api.domain.model.Stop
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal

class StopEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StopEntity>(StopTable)
    var routeId by StopTable.routeId
    var noticeId by StopTable.noticeId
    var stopStateId by StopTable.stopStateId
    var recipientName by StopTable.recipientName
    var direction by StopTable.direction
    var directionPlaceId by StopTable.directionPlaceId
    private var _latitude by StopTable.latitude
    var latitude: Double
        get() = _latitude.toDouble()
        set(v) {
            _latitude = BigDecimal.valueOf(v)
        }
    private var _longitude by StopTable.longitude
    var longitude: Double
        get() = _longitude.toDouble()
        set(v) {
            _longitude = BigDecimal.valueOf(v)
        }
    var order by StopTable.order
    var note by StopTable.note
    val notice by NoticeEntity referencedOn StopTable.noticeId
    val stopState by StopStateEntity referencedOn StopTable.stopStateId

    fun toStop() = Stop(
        id = id.value,
        notice = notice.description,
        state = stopState.description,
        recipient = recipientName,
        direction = direction,
        directionPlaceId = directionPlaceId,
        latitude = latitude,
        longitude = longitude,
        order = order,
        note = note
    )
}