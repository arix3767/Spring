package com.kowalczyk.studentclasses.converters.TeacherConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.model.Teacher;

public enum TeacherDtoToTeacherConverter implements Converter<TeacherDto, Teacher> {

    INSTANCE;

    @Override
    public Teacher convert(TeacherDto teacherDto) {
        return Teacher.builder()
                .name(teacherDto.getName())
                .email(teacherDto.getEmail())
                .id(teacherDto.getId())
                .build();
    }
}
