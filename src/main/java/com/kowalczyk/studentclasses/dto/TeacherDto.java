package com.kowalczyk.studentclasses.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto extends UserDataDto {

    private Long id;
    private String name;
    private String email;
}