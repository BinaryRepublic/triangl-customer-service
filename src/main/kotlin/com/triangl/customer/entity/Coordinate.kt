package com.triangl.customer.entity

import java.time.Instant
import java.util.*

class Coordinate (
    var id: String? = null,

    var x: Float? = null,

    var y: Float? = null,

    var lastUpdatedAt: String? = null,

    var createdAt: String? = null
) {
    init {
        id = id ?: UUID.randomUUID().toString()
        createdAt = createdAt ?: Instant.now().toString()
        lastUpdatedAt = lastUpdatedAt ?: Instant.now().toString()
    }
}