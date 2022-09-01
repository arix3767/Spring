package com.kowalczyk.studentclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends UserData {
    private String name;
    @OneToOne(mappedBy = "teacher")
    private ClassRoom classRoom;
}
