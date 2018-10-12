package com.triangl.customer.webservices.pubsub

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.triangl.customer.CustomerApplication
import com.triangl.customer.entity.Customer
import com.triangl.customer.pubSubEntity.PubSubEvent
import com.triangl.customer.pubSubEntity.PubSubMessage
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("production")
class PubSubWsImp (
        private val messagingGateway: CustomerApplication.PubsubOutboundGateway
): PubSubWs {
    override fun sendCustomerToPubSub(customer: Customer) {
        val pubSubEvent = PubSubEvent(customer, "APPLY_CUSTOMER")
        val pubSubMessage = PubSubMessage(listOf(pubSubEvent))
        val jsonString = jacksonObjectMapper().writeValueAsString(pubSubMessage)
        messagingGateway.sendToPubsub(jsonString)
    }
}