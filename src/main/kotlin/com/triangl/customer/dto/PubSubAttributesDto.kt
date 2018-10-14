package com.triangl.customer.dto

class PubSubAttributesDto {

    lateinit var operation: PubSubOperation

    fun toHashMap() =
        hashMapOf(
            "operation" to operation.toString()
        )
}

enum class PubSubOperation {
    APPLY_CUSTOMER
}