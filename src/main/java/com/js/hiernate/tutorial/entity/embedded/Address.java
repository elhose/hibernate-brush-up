package com.js.hiernate.tutorial.entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String streetWithDetails;
    private String city;
    private String state;
    private String zipCode;
}
