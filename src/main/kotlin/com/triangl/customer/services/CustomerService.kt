package com.triangl.customer.services

import com.triangl.customer.entity.Customer
import com.triangl.customer.webservices.datastore.DatastoreWs
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

@Service("customerService")
class CustomerService (
    private val datastoreWs: DatastoreWs
) {

    fun findAllCustomer(): List<Customer> = datastoreWs.findAllCustomer()

    fun findCustomerById(customerId: String): Customer? = datastoreWs.findCustomerById(customerId)

    fun createCustomer(name: String): Customer? {
        val customer = Customer(name)
        datastoreWs.saveCustomer(customer)

        return datastoreWs.findCustomerById(customer.id!!)
    }

    fun updateCustomer(customerId: String, valuesToUpdate: Customer): Customer {
        val customer = datastoreWs.findCustomerById(customerId)!!

        val wasUpdated = customer.merge(valuesToUpdate)

        if (wasUpdated) {
            customer.lastUpdatedAt = Instant.now().toString()
            datastoreWs.saveCustomer(customer)
        }

        return customer
    }

    fun deleteCustomer(customerId: String): Boolean {
        datastoreWs.deleteCustomerById(customerId)
        return true
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
}