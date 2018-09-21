package com.triangl.customer.webservices.datastore

import com.triangl.customer.entity.Customer

interface DatastoreWs {

    fun findAllCustomer(): List<Customer>

    fun findCustomerById(customerId: String): Customer?

    fun saveCustomer(customer: Customer)

    fun deleteCustomerById(customerId: String)
}