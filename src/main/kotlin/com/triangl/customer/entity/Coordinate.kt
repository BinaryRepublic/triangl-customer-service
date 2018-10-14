package com.triangl.customer.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Coordinate {
    @NotNull
    var id: String? = null

    @NotNull
    var x: Float? = null

    @NotNull
    var y: Float? = null

    @NotNull
    var lastUpdatedAt: String? = null

    @NotNull
    var createdAt: String? = null

    @Suppress("unused")
    constructor()

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    init {
        this.id = UUID.randomUUID().toString()
        this.createdAt = Instant.now().toString()
        this.lastUpdatedAt = Instant.now().toString()
    }
}