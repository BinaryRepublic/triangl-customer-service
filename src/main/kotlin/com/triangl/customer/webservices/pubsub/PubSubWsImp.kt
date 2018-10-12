package com.triangl.customer.webservices.pubsub

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.triangl.customer.CustomerApplication
import com.triangl.customer.entity.Customer
import com.triangl.customer.pubSubEntity.OperationType
import com.triangl.customer.pubSubEntity.PubSubMessageDto
import com.triangl.customer.pubSubEntity.PubSubDto
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("production")
class PubSubWsImp (
        private val messagingGateway: CustomerApplication.PubsubOutboundGateway
): PubSubWs {
    override fun sendCustomerToPubSub(customer: Customer) {
        val pubSubEvent = PubSubMessageDto(customer, OperationType.APPLY_CUSTOMER)
        val pubSubMessage = PubSubDto(listOf(pubSubEvent))
        val jsonString = jacksonObjectMapper().writeValueAsString(pubSubMessage)
        messagingGateway.sendToPubsub(jsonString)
    }
}