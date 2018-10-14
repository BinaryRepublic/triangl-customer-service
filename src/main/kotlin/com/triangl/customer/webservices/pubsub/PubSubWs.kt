package com.triangl.customer.webservices.pubsub

import com.triangl.customer.entity.Customer

interface PubSubWs {
    fun sendCustomerToPubSub(customer: Customer)
}