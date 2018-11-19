package com.triangl.customer

import com.googlecode.objectify.ObjectifyFilter
import com.googlecode.objectify.ObjectifyService
import com.triangl.customer.entity.Customer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

@Component
@Profile("production")
class ObjectifyWebFilter : ObjectifyFilter()

@SpringBootApplication
@PropertySources(PropertySource("classpath:application.properties"), PropertySource("classpath:auth0.properties"))
class CustomerApplication

fun main(args: Array<String>) {

    ObjectifyService.init()
    ObjectifyService.register(Customer::class.java)

    runApplication<CustomerApplication>(*args)

}
