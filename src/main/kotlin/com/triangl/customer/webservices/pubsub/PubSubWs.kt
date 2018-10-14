package com.triangl.customer.webservices.pubsub

import com.triangl.customer.dto.PubSubAttributesDto

interface PubSubWs {

    fun publish(data: Any, attributes: PubSubAttributesDto)
}