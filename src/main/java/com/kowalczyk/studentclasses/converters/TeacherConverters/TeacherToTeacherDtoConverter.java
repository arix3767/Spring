package com.kowalczyk.studentclasses.converters.TeacherConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.model.Teacher;

public enum TeacherToTeacherDtoConverter implements Converter<Teacher, TeacherDto> {

    INSTANCE;

    @Override
    public TeacherDto convert(Teacher teacher) {
        return TeacherDto.builder()
                .name(teacher.getName())
                .email(teacher.getEmail())
                .id(teacher.getId())
                .build();
    }
}