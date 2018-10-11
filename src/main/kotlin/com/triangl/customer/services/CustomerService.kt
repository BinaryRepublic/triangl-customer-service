package com.triangl.customer.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.triangl.customer.CustomerApplication
import com.triangl.customer.entity.Customer
import com.triangl.customer.pubSubEntity.PubSubEvent
import com.triangl.customer.pubSubEntity.PubSubMessage
import com.triangl.customer.webservices.datastore.DatastoreWs
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

@Service("customerService")
class CustomerService (
    private val datastoreWs: DatastoreWs,
    private val messagingGateway: CustomerApplication.PubsubOutboundGateway
) {

    fun findAllCustomers(): List<Customer> = datastoreWs.findAllCustomers()

    fun findCustomerById(customerId: String): Customer? = datastoreWs.findCustomerById(customerId)

    fun createCustomer(name: String): Customer? {
        val customer = Customer(name)
        datastoreWs.saveCustomer(customer)

        val dbCustomer = datastoreWs.findCustomerById(customer.id!!)!!
        sendCustomerToPubSub(dbCustomer)

        return dbCustomer
    }

    fun updateCustomer(customerId: String, valuesToUpdate: Customer): Customer {
        val customer = datastoreWs.findCustomerById(customerId)!!

        val wasUpdated = customer.merge(valuesToUpdate)

        if (wasUpdated && valuesToUpdate.maps != null) {
            customer.maps!!.map { it.lastUpdatedAt = Instant.now().toString() }
        }

        if (wasUpdated) {
            customer.lastUpdatedAt = Instant.now().toString()
            datastoreWs.saveCustomer(customer)
        }

        val dbCustomer = datastoreWs.findCustomerById(customerId)!!
        sendCustomerToPubSub(dbCustomer)

        return dbCustomer
    }

    fun deleteCustomer(customerId: String) {
        datastoreWs.deleteCustomerById(customerId)
    }

    private inline infix fun <reified T : Any> T.merge(other: T): Boolean {
        var wasUpdated = false

        val nameToProperty = T::class.declaredMemberProperties.associateBy { it.name }
        for (entry in nameToProperty) {
            val mutualProperty = entry.value as KMutableProperty<*>
            if (entry.value.get(other) != null
             && entry.value.get(other) != entry.value.get(this)
             && entry.key != "id") {

                mutualProperty.setter.call(this, entry.value.get(other))
                wasUpdated = true
            }
        }

        return wasUpdated
    }

    fun sendCustomerToPubSub(customer: Customer) {
        val pubSubEvent = PubSubEvent(customer, "APPLY_CUSTOMER")
        val pubSubMessage = PubSubMessage(listOf(pubSubEvent))
        val jsonString = jacksonObjectMapper().writeValueAsString(pubSubMessage)
        messagingGateway.sendToPubsub(jsonString)
    }
}