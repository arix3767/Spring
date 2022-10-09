package com.kowalczyk.studentclasses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends UserDataDto {

    private String name;
    private String newEmail;
    private String teacher;
    private float rate;
    private AddressDto address;
}
