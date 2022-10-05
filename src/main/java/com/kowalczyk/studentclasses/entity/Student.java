package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserData {

    private String name;
    private String teacherName;
    private float rate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToMany
    @JoinTable(name = "students_schools",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "school_id"))
    private List<School> schools;
}
