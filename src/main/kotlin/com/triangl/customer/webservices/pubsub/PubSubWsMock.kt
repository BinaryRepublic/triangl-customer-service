package com.triangl.customer.webservices.pubsub

import com.triangl.customer.entity.Customer
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class PubSubWsMock: PubSubWs {
    override fun sendCustomerToPubSub(customer: Customer) { }
}