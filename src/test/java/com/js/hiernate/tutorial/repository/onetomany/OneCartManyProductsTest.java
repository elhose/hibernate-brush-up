package com.js.hiernate.tutorial.repository.onetomany;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.onetomany.Cart;
import com.js.hiernate.tutorial.entity.onetomany.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OneCartManyProductsTest extends PostgresInitializer {

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    void cleanUp() {
        cartRepository.deleteAll();
    }

    @Test
    @DisplayName("Save Cart with products only using CartRepo")
    void saveWorksCartFirstProductsLater() {
        final var cart = Cart.builder()
                             .build();
        var firstProduct = Product.builder()
                                  .name("Tea")
                                  .price(BigDecimal.valueOf(2.0))
                                  .cart(cart)
                                  .build();
        var secondProduct = Product.builder()
                                   .name("Coffee")
                                   .price(BigDecimal.valueOf(5.0))
                                   .cart(cart)
                                   .build();
        var thirdProduct = Product.builder()
                                  .name("Snack Bar")
                                  .price(BigDecimal.valueOf(3.0))
                                  .cart(cart)
                                  .build();

        cart.setProducts(List.of(firstProduct, secondProduct, thirdProduct));

        var savedCart = cartRepository.save(cart);

        assertThat(savedCart.getId()).isNotZero();
        assertThat(savedCart.getProducts())
                .hasSize(3)
                .contains(firstProduct, secondProduct, thirdProduct);
    }

}