package com.triangl.customer.webservices.datastore

import com.googlecode.objectify.ObjectifyService.ofy
import com.triangl.customer.entity.Customer
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("production")
class DatastoreWsImp: DatastoreWs {

    override fun findAllCustomers(): List<Customer> = ofy().load().type(Customer::class.java).list()

    override fun findCustomerById(customerId: String): Customer? = ofy().load().type(Customer::class.java).id(customerId).now()

    override fun saveCustomer(customer: Customer) {
        ofy().save().entity(customer).now()
    }

    override fun deleteCustomerById(customerId: String) {
        ofy().delete().type(Customer::class.java).id(customerId).now()
    }
}