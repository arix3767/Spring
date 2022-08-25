package com.kowalczyk.studentclasses.converters;

import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.model.Student;

public enum StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    INSTANCE;

    @Override
    public StudentDto convert(Student student) {
        return StudentDto.builder()
                .email(student.getEmail())
                .name(student.getName())
                .teacher(student.getTeacherName())
                .rate(student.getRate())
                .build();
    }
}
