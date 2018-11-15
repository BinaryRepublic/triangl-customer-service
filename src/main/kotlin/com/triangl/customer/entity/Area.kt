package com.triangl.customer.entity

import java.time.Instant
import java.util.*

class Area (
    var id: String? = null,

    var vertices: List<Coordinate>? = null,

    var createdAt: String? = null,

    var lastUpdatedAt: String? = null
) {
    init {
        id = id ?: UUID.randomUUID().toString()
        createdAt = createdAt ?: Instant.now().toString()
        lastUpdatedAt = lastUpdatedAt ?: Instant.now().toString()
    }
}