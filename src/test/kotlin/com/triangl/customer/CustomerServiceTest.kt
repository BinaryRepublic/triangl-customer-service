package com.triangl.customer

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.triangl.customer.entity.Coordinate
import com.triangl.customer.entity.Customer
import com.triangl.customer.entity.Map
import com.triangl.customer.entity.Router
import com.triangl.customer.services.CustomerService
import com.triangl.customer.webservices.datastore.DatastoreWs
import com.triangl.customer.webservices.pubsub.PubSubWs
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@RunWith(MockitoJUnitRunner::class)
@SpringBootTest
class CustomerServiceTest{

    @Mock
    private lateinit var datastoreWs: DatastoreWs

    @Mock
    private lateinit var pubsubWs: PubSubWs

    @InjectMocks
    private lateinit var customerService: CustomerService

    @Test
    fun `Should create Customer and call saveCustomer and findCustomer`() {
        /* Given */
        given(datastoreWs.findCustomerById(anyString())).willReturn(Customer("TestName"))

        /* When */
        val createdCustomer = customerService.createCustomer("TestName")

        /* Then */
        assertThat(createdCustomer).isNotNull
        verify(datastoreWs).saveCustomer(any())
        verify(datastoreWs).findCustomerById(anyString())
    }

    @Test
    fun `Should not update Customer because entities are equal`() {
        /* Given */
        val initialCustomer = Customer("TestName")
        val newCustomer = Customer("TestName")
        newCustomer.apply {
            id = initialCustomer.id
            createdAt = initialCustomer.createdAt
            lastUpdatedAt = initialCustomer.lastUpdatedAt
        }

        given(datastoreWs.findCustomerById(initialCustomer.id!!)).willReturn(initialCustomer)

        /* When */
        val updatedCustomer = customerService.updateCustomer(initialCustomer.id!!, newCustomer)

        /* Then */
        assertThat(updatedCustomer).isNotNull
    }

    @Test
    fun `Should update Customer but not the Id`() {
        /* Given */
        val initialCustomer = Customer("TestName")
        val newCustomer = Customer("TestName")
        newCustomer.apply {
            createdAt = initialCustomer.createdAt
            name = "Updated"
            maps = listOf(
                Map(
                    name = "TestMap",
                    svgPath = "TestPath",
                    size = Coordinate(x = 1F, y = 1F),
                    router = listOf(Router(location = Coordinate(x = 2F, y = 2F)))
                )
            )
        }

        given(datastoreWs.findCustomerById(initialCustomer.id!!)).willReturn(initialCustomer)

        /* When */
        val updatedCustomer = customerService.updateCustomer(initialCustomer.id!!, newCustomer)

        /* Then */
        assertThat(updatedCustomer).isNotNull

        assertThat(updatedCustomer.id).isNotEqualTo(newCustomer.id)
        assertThat(updatedCustomer.maps).isEqualTo(newCustomer.maps)
        assertThat(updatedCustomer.name).isEqualTo(newCustomer.name)
        assertThat(updatedCustomer.lastUpdatedAt).isNotEqualTo(newCustomer.lastUpdatedAt)
    }
}
