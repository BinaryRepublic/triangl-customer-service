package com.triangl.customer.entity

import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import java.time.Instant
import java.util.*
import javax.persistence.Entity
import kotlin.collections.ArrayList

@Entity
@com.googlecode.objectify.annotation.Entity
class Customer (
    @Id
    var id: String? = null,

    var name: String? = null,

    @Index
    var maps: List<Map>? = null,

    var lastUpdatedAt: String? = null,

    var createdAt: String? = null
) {
    constructor(inputName: String): this(name = inputName) {
        id = id ?: UUID.randomUUID().toString()
        maps = maps ?: ArrayList()
        createdAt = createdAt ?: Instant.now().toString()
        lastUpdatedAt = lastUpdatedAt ?: Instant.now().toString()
    }
}