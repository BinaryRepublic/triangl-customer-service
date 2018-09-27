package com.triangl.customer.entity

import java.util.*
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Router {
    @NotNull
    var id: String? = null

    @NotNull
    var location: Coordinate? = null

    @Suppress("unused")
    constructor()

    constructor(x: Float, y: Float) {
        this.id = UUID.randomUUID().toString()
        this.location = Coordinate(x,y)
    }
}