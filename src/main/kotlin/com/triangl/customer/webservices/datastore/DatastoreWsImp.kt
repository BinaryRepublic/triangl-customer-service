package com.triangl.customer.webservices.datastore

import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService.ofy
import com.triangl.customer.entity.Customer

class DatastoreWsImp: DatastoreWs {
    override fun findAllCustomer(): List<Customer> = ofy().load().type(Customer::class.java).list()

    override fun findCustomerById(customerId: String): Customer? = ofy().load().type(Customer::class.java).id(customerId).now()

    override fun saveCustomer(entity: Customer) { ofy().save().entity(entity).now() }

    override fun deleteCustomerById(customerId: String) { ofy().delete().type(Customer::class.java).id(customerId).now() }
}