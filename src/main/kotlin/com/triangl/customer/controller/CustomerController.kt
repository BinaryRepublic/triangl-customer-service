package com.triangl.customer.controller

import com.triangl.customer.entity.Customer
import com.triangl.customer.services.CustomerService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class CustomerController (
    private val customerService: CustomerService
) {

    @ApiOperation(value = "Get all Customers (only for development. To be removed later)", response = Customer::class, responseContainer = "List")
    @GetMapping("/all")
    fun findAll(): ResponseEntity<*> {
        val customerList = customerService.findAllCustomers()

        return ResponseEntity.ok().body(hashMapOf("customers" to customerList))
    }

    @ApiOperation(value = "Get a Customer by his Id", response = Customer::class)
    @ApiResponses(value = [ApiResponse(code = 400, message = "Customer ID not found")])
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<*> {
        val customer = customerService.findCustomerById(id)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Customer ID not found"))
        } else {
            ResponseEntity.ok().body(customer)
        }
    }

    @ApiOperation(value = "Create a Customer with the given name", response = Customer::class)
    @ApiResponses(value = [ApiResponse(code = 400, message = "Customer ID not found")])
    @PostMapping
    fun createWithName(@RequestBody name: String): ResponseEntity<*> {
        val customer = customerService.createCustomer(name)

        return if (customer == null) {
            ResponseEntity.status(400).body(hashMapOf("error" to "Couldn't create customer"))
        } else {
            ResponseEntity.ok().body(customer)
        }
    }

    @ApiOperation(value = "Updates a Customers with the given id", response = Customer::class)
    @PatchMapping("/{id}")
    fun updateById(@PathVariable id: String, @RequestBody @ApiParam(value = "should only contain the values to be updated") valuesToUpdate: Customer): ResponseEntity<*> {
        val customer = customerService.updateCustomer(id, valuesToUpdate)

        return ResponseEntity.ok().body(customer)
    }

    @ApiOperation(value = "Delete the customer with the given id")
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<Void> {
        customerService.deleteCustomer(id)

        return ResponseEntity.noContent().build()
    }
}