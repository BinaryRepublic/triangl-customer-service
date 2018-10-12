package com.triangl.customer.pubSubEntity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.triangl.customer.entity.Customer
import java.util.*

class PubSubMessageDto (
        dataObject: Customer,
        operation: OperationType,
        additional: HashMap<String, String>? = HashMap()

) {
    val dataByteArray: ByteArray = jacksonObjectMapper().writeValueAsBytes(dataObject)
    val data = Base64.getEncoder().encodeToString(dataByteArray)
    val attributes = PubSubMessageAttributeDto(operation, additional)
}