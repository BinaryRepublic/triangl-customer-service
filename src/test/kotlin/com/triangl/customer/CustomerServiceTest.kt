package com.triangl.customer

import com.triangl.customer.entity.Customer
import com.triangl.customer.services.CustomerService
import com.triangl.customer.webservices.datastore.DatastoreWs
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.any
import com.triangl.customer.entity.Coordinate
import com.triangl.customer.entity.Map
import com.triangl.customer.entity.Router
import org.mockito.ArgumentMatchers.anyString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CustomerServiceTest{

    @Mock
    private
    lateinit var datastoreWs: DatastoreWs

    @InjectMocks
    lateinit var customerService: CustomerService

    @Test
    fun `Should create Customer and call saveCustomer and findCustomer`() {
        /* Given */
        given(datastoreWs.findCustomerById(anyString())).willReturn(Customer("TestName"))

        /* When */
        val created = customerService.createCustomer("TestName")

        /* Then */
        assertThat(created).isTrue()
        verify(datastoreWs).saveCustomer(any())
        verify(datastoreWs).findCustomerById(anyString())
    }

    @Test
    fun `Should not update Customer because entities are equal`() {
        /* Given */
        val initialCustomer = Customer("TestName")
        val newCustomer = Customer("TestName")
        newCustomer.apply { id = initialCustomer.id
                            createdAt = initialCustomer.createdAt
                            lastUpdatedAt = initialCustomer.lastUpdatedAt }

        given(datastoreWs.findCustomerById(initialCustomer.id!!)).willReturn(initialCustomer)

        /* When */
        val wasUpdated = customerService.updateCustomer(initialCustomer.id!!, newCustomer)

        /* Then */
        assertThat(wasUpdated).isFalse()

    }

    @Test
    fun `Should update Customer but not the Id`() {
        /* Given */
        val initialCustomer = Customer("TestName")
        val newCustomer = Customer("TestName")
        newCustomer.apply { createdAt = initialCustomer.createdAt
                            deleted = true
                            maps = listOf(Map("TestMap","TestPath", Coordinate(1F,1F), listOf(Router(2F,2F))))}

        given(datastoreWs.findCustomerById(initialCustomer.id!!)).willReturn(initialCustomer)

        /* When */
        val wasUpdated = customerService.updateCustomer(initialCustomer.id!!, newCustomer)

        /* Then */
        assertThat(wasUpdated).isTrue()

        assertThat(initialCustomer.id).isNotEqualTo(newCustomer.id)
        assertThat(initialCustomer.maps).isEqualTo(newCustomer.maps)
        assertThat(initialCustomer.deleted).isEqualTo(newCustomer.deleted)
        assertThat(initialCustomer.lastUpdatedAt).isNotEqualTo(newCustomer.lastUpdatedAt)
    }
}
