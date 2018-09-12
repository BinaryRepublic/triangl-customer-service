package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import org.springframework.web.bind.annotation.*
import com.googlecode.objectify.ObjectifyService.ofy

@RestController
@RequestMapping("/customer")
class CustomerController {

    // Just for development - Remove it later
    @GetMapping("/all")
    fun findAll(): List<Customer> {
        return ofy().load().type(Customer::class.java).list()
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): Customer {
        return ofy().load().type(Customer::class.java).id(id).now()
    }

    @PostMapping
    fun createUser(@RequestBody name: String): Customer {

        val cust = Customer(name=name)
        ofy().save().entity(cust).now()

        val result: Customer = ofy().load().type(Customer::class.java).id(cust.id).now()
        return result
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) {
        ofy().delete().type(Customer::class.java).id(id).now()
    }
}