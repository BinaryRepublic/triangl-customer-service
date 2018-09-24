package com.triangl.customer

import com.googlecode.objectify.ObjectifyFilter
import com.googlecode.objectify.ObjectifyService
import com.triangl.customer.entity.Coordinate
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map
import com.triangl.customer.entity.Router
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("production")
class ObjectifyWebFilter : ObjectifyFilter()

@SpringBootApplication
class CustomerApplication
fun main(args: Array<String>) {

    ObjectifyService.init()
    ObjectifyService.register(Customer::class.java)
    ObjectifyService.register(Map::class.java)
    ObjectifyService.register(Router::class.java)
    ObjectifyService.register(Coordinate::class.java)

    runApplication<CustomerApplication>(*args)

}
