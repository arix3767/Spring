package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    private int classNumber;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoom)) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return !(id == null && classRoom.id == null)
                && classNumber == classRoom.classNumber
                && Objects.equals(id, classRoom.id)
                && Objects.equals(school, classRoom.school)
                && Objects.equals(teacher, classRoom.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classNumber, school, teacher);
    }
}
