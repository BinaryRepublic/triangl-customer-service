package com.triangl.customer.webservices.datastore

import com.googlecode.objectify.Key
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map

class DatastoreWsMock: DatastoreWs {
    var testCustomer = arrayListOf(Customer("TestCustomer"))

    override fun findAllCustomer(): List<Customer> = testCustomer

    override fun findCustomerById(customerId: String): Customer? = testCustomer[0]

    override fun saveCustomer(entity: Customer) {}

    override fun deleteCustomerById(customerId: String) {}
}