package com.kowalczyk.studentclasses.entity;

import com.kowalczyk.studentclasses.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Subject subject;
    private LocalDateTime term;
    private String student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classes)) return false;
        Classes classes = (Classes) o;
        return !(id == null && classes.id == null)
                && Objects.equals(id, classes.id)
                && subject == classes.subject
                && Objects.equals(term, classes.term)
                && Objects.equals(student, classes.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, term, student);
    }
}
