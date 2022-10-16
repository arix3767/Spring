package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    private String city;
    private int postalCode;
    private String street;
    private int homeNumber;
    private int flatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return !(id == null && address.id == null)
                && postalCode == address.postalCode
                && homeNumber == address.homeNumber
                && flatNumber == address.flatNumber
                && Objects.equals(id, address.id)
                && Objects.equals(city, address.city)
                && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city,
                postalCode,
                street,
                homeNumber,
                flatNumber);
    }
}
