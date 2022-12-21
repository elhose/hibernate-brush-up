package com.js.hiernate.tutorial.entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_table")
@AttributeOverrides({
        @AttributeOverride(
                name = "billingAddress.streetWithDetails",
                column = @Column(name = "billing_address_street_with_details")
        ),
        @AttributeOverride(
                name = "billingAddress.city",
                column = @Column(name = "billing_address_city")
        ),
        @AttributeOverride(
                name = "billingAddress.state",
                column = @Column(name = "billing_address_state")
        ),
        @AttributeOverride(
                name = "billingAddress.zipCode",
                column = @Column(name = "billing_address_zip_code")
        ),
        @AttributeOverride(
                name = "shippingAddress.streetWithDetails",
                column = @Column(name = "shipping_address_street_with_details")
        ),
        @AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_address_city")
        ),
        @AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_address_state")
        ),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_address_zip_code")
        ),
})
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String consumer;
    @Embedded
    private Address billingAddress;
    @Embedded
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
