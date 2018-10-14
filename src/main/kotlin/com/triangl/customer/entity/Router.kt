package com.triangl.customer.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Router {
    @NotNull
    var id: String? = null

    @NotNull
    var location: Coordinate? = null

    @NotNull
    var lastUpdatedAt: String? = null

    @NotNull
    var createdAt: String? = null

    @Suppress("unused")
    constructor()

    constructor(x: Float, y: Float) {
        this.location = Coordinate(x,y)
    }

    init {
        this.id = UUID.randomUUID().toString()
        this.createdAt = Instant.now().toString()
        this.lastUpdatedAt = Instant.now().toString()
    }
}