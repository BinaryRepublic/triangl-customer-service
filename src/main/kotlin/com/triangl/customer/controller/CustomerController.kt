package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import org.springframework.web.bind.annotation.*
import com.triangl.customer.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

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
        val created = customerService.createCustomer(name)

        return if (created == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(hashMapOf("customer" to created))
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: String, @RequestBody valuesToUpdate: Customer): ResponseEntity<*> {
        val updated = customerService.updateCustomer(id, valuesToUpdate)

        return ResponseEntity.ok().body(hashMapOf("updated" to updated))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<*> {
        val deleted = customerService.deleteCustomer(id)

        return ResponseEntity.ok().body(hashMapOf("deleted" to deleted))
    }
}