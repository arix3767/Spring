package com.kowalczyk.studentclasses.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private String email;
    private String teacher;
    private float rate;
}
