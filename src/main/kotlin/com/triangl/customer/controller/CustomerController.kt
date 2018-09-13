package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import org.springframework.web.bind.annotation.*
import com.googlecode.objectify.ObjectifyService.ofy
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/customer")
class CustomerController {

    // Just for development - Remove it later
    @GetMapping("/all")
    fun findAll(): ResponseEntity<*> {

        val result = ofy().load().type(Customer::class.java).list()
        return ResponseEntity.ok().body(hashMapOf("customerList" to result))
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): ResponseEntity<*> {
        val result = ofy().load().type(Customer::class.java).id(id).now()

        return if (result == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(hashMapOf("customer" to result))
        }
    }

    @PostMapping
    fun createUser(@RequestBody name: String): ResponseEntity<*> {

        val cust = Customer(name=name)
        ofy().save().entity(cust).now()

        val result: Customer = ofy().load().type(Customer::class.java).id(cust.id).now()

        return if (result == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(hashMapOf("customer" to result))
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody valuesToUpdate: HashMap<String, *>): ResponseEntity<*> {

        var wasUpdated = false
        var customer = ofy().load().type(Customer::class.java).id(id).now()

        for ((key, value) in valuesToUpdate) {
            wasUpdated = customer.updateIfPresent(key, value)
        }

        if (wasUpdated) {
            ofy().save().entity(customer).now()
        }

        return ResponseEntity.ok().body(hashMapOf("updated" to wasUpdated))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<*> {
        ofy().delete().type(Customer::class.java).id(id).now()

        return ResponseEntity.ok().body(hashMapOf("deleted" to true))
    }
}