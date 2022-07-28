package com.kowalczyk.studentclasses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String name;
    private String email;
    private String newEmail;
    private String teacher;
    private float rate;
}
