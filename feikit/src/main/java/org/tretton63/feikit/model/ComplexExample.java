package org.tretton63.feikit.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class ComplexExample {
    private String name;
    private Address address;
    private List<String> objects;

    public ComplexExample() {}
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Address {
        private String postalCode;
        private String street;
        private String city;
    }
}
