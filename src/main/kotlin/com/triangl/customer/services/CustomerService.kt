package com.triangl.customer.services

import com.googlecode.objectify.ObjectifyService.ofy
import com.triangl.customer.entity.Customer
import com.triangl.customer.webservices.datastore.DatastoreWsImp
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

@Service("customerService")
class CustomerService (
    private val datastoreWs: DatastoreWsImp
) {

    fun findAllCustomer(): List<Customer> = datastoreWs.findAllCustomer()

    fun findCustomerById(customerId: String): Customer? = datastoreWs.findCustomerById(customerId)

    fun createCustomer(name: String): Boolean {
        val customer = Customer(name)
        ofy().save().entity(customer).now()

        val result: Customer = ofy().load().type(Customer::class.java).id(customer.id).now()

        return result != null
    }

    fun updateCustomer(customerId: String, valuesToUpdate: Customer): Boolean {
        val customer = ofy().load().type(Customer::class.java).id(customerId).now()

        val wasUpdated = customer.merge(valuesToUpdate)

        if (wasUpdated) {
            customer.lastUpdatedAt = Instant.now().toString()
            ofy().save().entity(customer).now()
        }

        return wasUpdated
    }

    fun deleteCustomer(customerId: String): Boolean {
        ofy().delete().type(Customer::class.java).id(customerId).now()
        return true
    }

    private inline infix fun <reified T : Any> T.merge(other: T): Boolean {
        var wasUpdated = false

        val nameToProperty = T::class.declaredMemberProperties.associateBy { it.name }
        for (entry in nameToProperty) {
            val mutualProperty = entry.value as KMutableProperty<*>
            if (entry.value.get(other) != null && entry.value.get(other) != entry.value.get(this)) {
                mutualProperty.setter.call(this, entry.value.get(other))
                wasUpdated = true
            }
        }

        return wasUpdated
    }
}