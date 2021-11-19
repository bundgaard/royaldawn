package org.tretton63.feikit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ComplexExample {
    private String name;
    private Address address;
    private List<String> objects;

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Address {
        private String postalCode;
        private String street;
        private String city;
    }
}
