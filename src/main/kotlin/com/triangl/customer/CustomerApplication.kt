package com.triangl.customer

import com.googlecode.objectify.ObjectifyFilter
import com.googlecode.objectify.ObjectifyService
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@Component
class ObjectifyWebFilter : ObjectifyFilter()

@SpringBootApplication
class CustomerApplication
fun main(args: Array<String>) {


    ObjectifyService.init()
    ObjectifyService.register(Customer::class.java)
    ObjectifyService.register(Map::class.java)

    runApplication<CustomerApplication>(*args)

}
