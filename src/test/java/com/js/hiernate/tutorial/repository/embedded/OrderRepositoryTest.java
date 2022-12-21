package com.js.hiernate.tutorial.repository.embedded;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.embedded.Address;
import com.js.hiernate.tutorial.entity.embedded.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest extends PostgresInitializer {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    void saveWorks() {
        var order = prepareOrder();

        final var savedOrder = orderRepository.save(order);

        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getConsumer()).isEqualTo(order.getConsumer());
        assertThat(savedOrder.getBillingAddress()).isEqualTo(order.getBillingAddress());
        assertThat(savedOrder.getShippingAddress()).isEqualTo(order.getShippingAddress());
    }

    @Test
    void readWorks() {
        var order = prepareOrder();
        final var savedOrder = orderRepository.save(order);

        final var foundOrder = orderRepository.findById(savedOrder.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(savedOrder.getId());
        assertThat(foundOrder.get().getConsumer()).isEqualTo(order.getConsumer());
        assertThat(foundOrder.get().getBillingAddress()).isEqualTo(order.getBillingAddress());
        assertThat(foundOrder.get().getShippingAddress()).isEqualTo(order.getShippingAddress());
    }

    @Test
    void updateWorks() {
        var order = prepareOrder();
        final var savedOrder = orderRepository.save(order);

        savedOrder.setShippingAddress(Address.builder()
                                             .streetWithDetails("UPDATED STREET")
                                             .city("UPDATED CITY")
                                             .state("UPDATED STATE")
                                             .zipCode("UPDATED ZIPCODE")
                                             .build());

        final var updatedOrder = orderRepository.save(savedOrder);

        assertThat(updatedOrder.getId()).isEqualTo(savedOrder.getId());
        assertThat(updatedOrder.getConsumer()).isEqualTo(savedOrder.getConsumer());
        assertThat(updatedOrder.getBillingAddress()).isEqualTo(savedOrder.getBillingAddress());
        assertThat(updatedOrder.getShippingAddress()).isEqualTo(savedOrder.getShippingAddress());
    }

    @Test
    void deleteWorks() {
        var order = prepareOrder();
        final var savedOrder = orderRepository.save(order);

        orderRepository.delete(savedOrder);

        final var allOrders = orderRepository.findAll();
        assertThat(allOrders).isEmpty();
    }

    private Order prepareOrder() {
        return Order.builder()
                    .consumer("CONSUMER")
                    .billingAddress(Address.builder()
                                           .city("London")
                                           .state("ENG")
                                           .zipCode("123-456")
                                           .build())
                    .shippingAddress(Address.builder()
                                            .streetWithDetails("Nice Street")
                                            .city("Berlin")
                                            .state("DEEE")
                                            .zipCode("333-777")
                                            .build())
                    .build();
    }

}