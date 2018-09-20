package com.triangl.customer

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.triangl.customer.entity.Map
import com.triangl.customer.entity.Customer
import com.triangl.customer.services.CustomerService
import com.triangl.customer.controller.CustomerController
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.ArgumentMatchers.anyString
import org.springframework.http.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.hamcrest.Matchers.*


@RunWith(MockitoJUnitRunner::class)
@WebMvcTest
class CustomerControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Mock
    lateinit var customerService: CustomerService

    @InjectMocks
    lateinit var customerController: CustomerController

    @Before
    fun init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build()
    }

    @Test
    fun `should return a list of all customers`() {
        /* Given */
        val customer1 =  Customer("Customer1")
        val customer2 = Customer("Customer2")
        val customerList = listOf(customer1, customer2)

        given(customerService.findAllCustomer()).willReturn(customerList)

        /* When, Then */
        mockMvc.perform(get("/customer/all"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.customerList", hasSize<Customer>(2)))
                .andExpect(jsonPath("$.customerList[0].id", `is`(customer1.id)))
                .andExpect(jsonPath("$.customerList[0].name", `is`(customer1.name)))
                .andExpect(jsonPath("$.customerList[1].id", `is`(customer2.id)))
                .andExpect(jsonPath("$.customerList[1].name", `is`(customer2.name)))
    }

    @Test
    fun `should return a customer by Id`() {
        /* Given */
        val customer3 =  Customer("Customer3")

        given(customerService.findCustomerById(customer3.id!!)).willReturn(customer3)

        /* When, Then */
        mockMvc.perform(get("/customer/${customer3.id}"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.customer.id", `is`(customer3.id)))
                .andExpect(jsonPath("$.customer.name", `is`(customer3.name)))
    }

    @Test
    fun `should return 400 when no customer with given Id is found`() {
        /* Given */
        val customer4 =  Customer("Customer4")

        given(customerService.findCustomerById(customer4.id!!)).willReturn(null)

        /* When, Then */
        mockMvc.perform(get("/customer/${customer4.id}"))
                .andDo(print())
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error", `is`("Customer ID not found")))
    }

    @Test
    fun `should return new created customer`() {
        /* Given */
        val name = "Customer5"

        given(customerService.createCustomer(name)).willReturn(Customer(name))

        /* When, Then */
        mockMvc.perform(post("/customer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(name))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.customer.name", `is`(name)))
                .andExpect(jsonPath("$.customer.deleted", `is`(false)))
                .andExpect(jsonPath("$.customer.maps", `is`(listOf<Map>())))
    }

    @Test
    fun `should return 400 when customer was not created`() {
        /* Given */
        val name = "Customer6"

        given(customerService.createCustomer(name)).willReturn(null)

        /* When, Then */
        mockMvc.perform(post("/customer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(name))
                .andDo(print())
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.error", `is`("Customer ID not found")))
    }

    @Test
    fun `should return updated customer`() {
        /* Given */
        val initialCustomer = Customer("Customer7")
        val newCustomer = Customer("Customer8")
        newCustomer.apply { id = initialCustomer.id
            deleted = true }

        given(customerService.updateCustomer(eq(initialCustomer.id!!), any<Customer>())).willReturn(newCustomer)

        /* When, Then */
        mockMvc.perform(patch("/customer/${initialCustomer.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"deleted\": true }"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.customer.id", `is`(initialCustomer.id)))
                .andExpect(jsonPath("$.customer.deleted", `is`(newCustomer.deleted)))
    }

    @Test
    fun `should return wasDeleted = true`() {
        /* Given */
        val customer9 = Customer("Customer9")
        given(customerService.deleteCustomer(anyString())).willReturn(true)

        /* When, Then */
        mockMvc.perform(delete("/customer/${customer9.id}"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.deleted", `is`(true)))
    }
}