package com.triangl.customer.entity

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

    @Suppress("unused")
    constructor()

    constructor(x: Float, y: Float) {
        this.id = UUID.randomUUID().toString()
        this.x = x
        this.y = y
    }
}