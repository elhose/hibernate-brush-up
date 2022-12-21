package com.js.hiernate.tutorial.repository.embedded;

import com.js.hiernate.tutorial.entity.embedded.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
