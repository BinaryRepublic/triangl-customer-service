package com.triangl.customer.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity

@Entity
class Map (
    var id: String? = null,

    var name: String? = null,

    var svgPath: String? = null,

    var size: Coordinate? = null,

    var router: List<Router>? = null,

    var lastUpdatedAt: String? = null,

    var createdAt: String? = null

) {
    init {
        id = id ?: UUID.randomUUID().toString()
        createdAt = createdAt ?: Instant.now().toString()
        lastUpdatedAt = lastUpdatedAt ?: Instant.now().toString()
    }
}