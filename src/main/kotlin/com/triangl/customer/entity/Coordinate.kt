package com.triangl.customer.entity

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import java.util.*
import javax.validation.constraints.NotNull

@javax.persistence.Entity
@Entity
class Coordinate {
    @Id
    @NotNull
    var id: String? = null

    @NotNull
    var x: Float? = null

    @NotNull
    var y: Float? = null

    constructor() {}

    constructor(x: Float, y: Float) {
        this.id = UUID.randomUUID().toString()
        this.x = x
        this.y = y
    }
}