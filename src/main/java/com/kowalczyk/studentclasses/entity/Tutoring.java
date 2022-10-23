package com.kowalczyk.studentclasses.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tutoring extends Classes {

    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tutoring)) return false;
        if (!super.equals(o)) return false;
        Tutoring tutoring = (Tutoring) o;
        return Objects.equals(price, tutoring.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }
}
