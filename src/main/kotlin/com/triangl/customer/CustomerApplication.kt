package com.triangl.customer

import com.googlecode.objectify.ObjectifyFilter
import com.googlecode.objectify.ObjectifyService
import com.triangl.customer.entity.Customer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
@Profile("production")
class ObjectifyWebFilter : ObjectifyFilter()

@SpringBootApplication
class CustomerApplication {

    @Bean
    @Profile("production")
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    fun messageSender(pubsubTemplate: PubSubOperations): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "test")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    interface PubsubOutboundGateway {

        fun sendToPubsub(text: String)
    }

}

fun main(args: Array<String>) {

    ObjectifyService.init()
    ObjectifyService.register(Customer::class.java)

    runApplication<CustomerApplication>(*args)

}
