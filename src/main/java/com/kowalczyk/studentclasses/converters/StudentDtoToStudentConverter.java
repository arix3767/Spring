package com.kowalczyk.studentclasses.converters;

import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.model.Student;

public enum StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

    INSTANCE;

    @Override
    public Student convert(StudentDto studentDto) {
        return Student.builder()
                .email(studentDto.getEmail())
                .name(studentDto.getName())
                .teacherName(studentDto.getTeacher())
                .rate(studentDto.getRate())
                .build();
    }
}
