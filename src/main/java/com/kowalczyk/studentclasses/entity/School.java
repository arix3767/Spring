package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int schoolNumber;
    private String websiteUrl;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "school")
    private List<ClassRoom> classRooms;
    @ManyToMany(mappedBy = "schools")
    private List<Student> students;
}
