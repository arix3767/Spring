package com.kowalczyk.studentclasses.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    private int schoolNumber;
    private String websiteUrl;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER)
    private List<ClassRoom> classRooms;
    @ToString.Exclude
    @ManyToMany(mappedBy = "schools")
    private List<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return !(id == null && school.id == null)
                && schoolNumber == school.schoolNumber
                && Objects.equals(id, school.id)
                && Objects.equals(websiteUrl, school.websiteUrl)
                && Objects.equals(address, school.address)
                && Objects.equals(classRooms, school.classRooms)
                && Objects.equals(students, school.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolNumber,
                websiteUrl,
                address,
                classRooms,
                students);
    }
}