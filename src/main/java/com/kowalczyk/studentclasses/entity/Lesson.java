package com.kowalczyk.studentclasses.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends Classes {

    private int schoolNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        if (!super.equals(o)) return false;
        Lesson lesson = (Lesson) o;
        return schoolNumber == lesson.schoolNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), schoolNumber);
    }
}
