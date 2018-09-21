package com.triangl.customer.webservices.datastore

import com.triangl.customer.entity.Customer
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class DatastoreWsMock: DatastoreWs {

    private fun customer(id: String): Customer {
        return Customer("name_$id").apply {
            this.id = id
        }
    }

    override fun findAllCustomer(): List<Customer> = arrayListOf(customer("c1"))

    override fun findCustomerById(customerId: String): Customer? = customer(customerId)

    override fun saveCustomer(customer: Customer) { }

    override fun deleteCustomerById(customerId: String) { }
}