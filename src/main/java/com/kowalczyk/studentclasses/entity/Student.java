package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserData {

    private String name;
    private String teacherName;
    private float rate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "students_schools",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id"))
    private List<School> schools;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Float.compare(student.rate, rate) == 0
                && Objects.equals(name, student.name)
                && Objects.equals(teacherName, student.teacherName)
                && Objects.equals(address, student.address)
                && Objects.equals(schools, student.schools);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teacherName, rate, address, schools);
    }
}
