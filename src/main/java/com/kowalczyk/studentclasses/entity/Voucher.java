package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    private BigDecimal price;
    @ManyToOne
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voucher)) return false;
        Voucher voucher = (Voucher) o;
        return !(id == null && voucher.id == null)
                && Objects.equals(id, voucher.id)
                && Objects.equals(price, voucher.price)
                && Objects.equals(student, voucher.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, student);
    }
}
