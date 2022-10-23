package com.kowalczyk.studentclasses.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends UserData {
    private String name;
    @OneToOne(mappedBy = "teacher")
    private ClassRoom classRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(name, teacher.name)
                && Objects.equals(classRoom, teacher.classRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, classRoom);
    }
}
