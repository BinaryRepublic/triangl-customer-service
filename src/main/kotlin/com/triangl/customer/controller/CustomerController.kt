package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import com.triangl.customer.services.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
class CustomerController (
    private val customerService: CustomerService
) {

    // Just for development - Remove it later
    @GetMapping("/all")
    fun findAll(): ResponseEntity<*> {
        val customerList = customerService.findAllCustomer()

        return ResponseEntity.ok().body(hashMapOf("customerList" to customerList))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<*> {
        val customer = customerService.findCustomerById(id)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(hashMapOf("customer" to customer))
        }
    }

    @PostMapping
    fun createWithName(@RequestBody name: String): ResponseEntity<*> {
        val customer = customerService.createCustomer(name)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(hashMapOf("customer" to customer))
        }
    }

    @PatchMapping("/{id}")
    fun updateById(@PathVariable id: String, @RequestBody valuesToUpdate: Customer): ResponseEntity<*> {
        val customer = customerService.updateCustomer(id, valuesToUpdate)

        return ResponseEntity.ok().body(hashMapOf("customer" to customer))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<*> {
        val wasDeleted = customerService.deleteCustomer(id)

        return ResponseEntity.ok().body(hashMapOf("deleted" to wasDeleted))
    }
}