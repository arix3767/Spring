package com.kowalczyk.studentclasses.model;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private String email;
    private String newEmail;
    private String teacher;
    private float rate;
}
