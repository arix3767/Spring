package com.kowalczyk.studentclasses.model;

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
    private int schoolNumber;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
