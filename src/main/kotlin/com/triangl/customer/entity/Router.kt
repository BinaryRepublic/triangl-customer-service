package com.triangl.customer.entity

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import java.util.*
import javax.validation.constraints.NotNull

@javax.persistence.Entity
@Entity
class Router {
    @Id
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