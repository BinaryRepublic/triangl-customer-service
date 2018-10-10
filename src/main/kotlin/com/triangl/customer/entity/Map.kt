package com.triangl.customer.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Map {
    @NotNull
    var id: String? = null

    @NotNull
    var name: String? = null

    @NotNull
    var svgPath: String? = null

    @NotNull
    var size: Coordinate? = null

    @NotNull
    var router: List<Router>? = null

    @NotNull
    var lastUpdatedAt: String? = null

    @NotNull
    var createdAt: String? = null

    @Suppress("unused")
    constructor()

    constructor(name: String, svgPath: String, size: Coordinate, routes: List<Router>) {
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.svgPath = svgPath
        this.size = size
        this.router = routes
    }

    init {
        this.createdAt = Instant.now().toString()
        this.lastUpdatedAt = Instant.now().toString()
        println("sad")
    }
}