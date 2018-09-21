package com.triangl.customer

import com.googlecode.objectify.ObjectifyFilter
import com.googlecode.objectify.ObjectifyService
import com.triangl.customer.entity.Coordinate
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map
import com.triangl.customer.entity.Router
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ObjectifyWebFilter : ObjectifyFilter()

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest {

    @Value("\${local.server.port}")
    private val serverPort: Int = 0

    @Before
    fun setUp() {
        ObjectifyService.init()
        ObjectifyService.register(Customer::class.java)
        ObjectifyService.register(Map::class.java)
        ObjectifyService.register(Router::class.java)
        ObjectifyService.register(Coordinate::class.java)

        RestAssured.port = serverPort
    }

    @Test
    fun `should return a list of customers`() {
        RestAssured.given()
                .get("/customer/all")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("customerList", hasSize<Customer>(1))
    }

    @Test
    fun `should return a customer by id`() {
        val customerId = "SomeRandomId"

        RestAssured.given()
                .get("/customer/$customerId")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("customer.id", `is`(customerId))
                .body("customer.name", `is`("name_$customerId"))
                .body("customer.maps", `is`(arrayListOf<Map>()))
    }

    @Test
    fun `should return the created customer`() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"TestUser\" }")
                .post("/customer")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("customer.id", isA(String::class.java))
                .body("customer.maps", `is`(arrayListOf<Map>()))
    }

    @Test
    fun `should return the updated customer`() {
        val customerId = "SomeRandomId"

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"updated\", \"deleted\": true }")
                .patch("/customer/$customerId")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("customer.id", `is`(customerId))
                .body("customer.name", `is`("updated"))
                .body("customer.deleted", `is`(true))

    }

    @Test
    fun `should return a boolean if the customer is deleted or not`() {
        RestAssured.given()
                .delete("/customer/SomeID")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("deleted", `is`(true))
    }
}