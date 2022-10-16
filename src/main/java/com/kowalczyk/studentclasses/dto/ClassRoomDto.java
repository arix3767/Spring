package com.kowalczyk.studentclasses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomDto {

    private Long id;
    private int classNumber;
    private SchoolDto schoolDto;
    private TeacherDto teacherDto;
}