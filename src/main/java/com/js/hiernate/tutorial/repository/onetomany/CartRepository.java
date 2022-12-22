package com.js.hiernate.tutorial.repository.onetomany;

import com.js.hiernate.tutorial.entity.onetomany.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
