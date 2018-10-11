package com.triangl.customer.pubSubEntity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.triangl.customer.entity.Customer
import java.util.*

class PubSubEvent (
        dataObject: Customer,
        operation: String,
        additional: HashMap<String, String>? = HashMap()

) {
    val data = Base64.getEncoder().encodeToString(jacksonObjectMapper().writeValueAsBytes(dataObject))
    val attributes = PubSubAttributes(operation, additional)
}