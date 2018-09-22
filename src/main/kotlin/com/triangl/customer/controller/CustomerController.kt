package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import com.triangl.customer.services.CustomerService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class CustomerController (
    private val customerService: CustomerService
) {

    // Just for development - Remove it later
    @GetMapping("/all")
    fun findAll(): ResponseEntity<*> {
        val customerList = customerService.findAllCustomers()

        return ResponseEntity.ok().body(hashMapOf("customers" to customerList))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<*> {
        val customer = customerService.findCustomerById(id)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(customer)
        }
    }

    @PostMapping
    fun createWithName(@RequestBody name: String): ResponseEntity<*> {
        val customer = customerService.createCustomer(name)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(customer)
        }
    }

    @PatchMapping("/{id}")
    fun updateById(@PathVariable id: String, @RequestBody valuesToUpdate: Customer): ResponseEntity<*> {
        val customer = customerService.updateCustomer(id, valuesToUpdate)

        return ResponseEntity.ok().body(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<Void> {
        customerService.deleteCustomer(id)

        return ResponseEntity.noContent().build()
    }
}