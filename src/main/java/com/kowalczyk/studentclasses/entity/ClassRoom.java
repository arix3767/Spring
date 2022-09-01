package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int classNumber;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
