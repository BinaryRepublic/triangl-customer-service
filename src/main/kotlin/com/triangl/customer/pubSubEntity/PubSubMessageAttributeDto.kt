package com.triangl.customer.pubSubEntity

class PubSubMessageAttributeDto (
        val operation: OperationType,
        val additional: HashMap<String, String>? = HashMap()
)

enum class OperationType {
    APPLY_CUSTOMER
}