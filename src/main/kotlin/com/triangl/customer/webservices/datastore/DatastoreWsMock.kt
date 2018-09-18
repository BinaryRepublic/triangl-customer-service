package com.triangl.customer.webservices.datastore

import com.googlecode.objectify.Key
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class DatastoreWsMock: DatastoreWs {
    var testCustomer = arrayListOf(Customer("TestCustomer"))

    override fun findAllCustomer(): List<Customer> = testCustomer

    override fun findCustomerById(customerId: String): Customer? = testCustomer[0]

    override fun saveCustomer(customer: Customer) {}

    override fun deleteCustomerById(customerId: String) {}
}