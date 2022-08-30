package com.kowalczyk.studentclasses.converters.ClassRoomConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.model.ClassRoom;
import com.kowalczyk.studentclasses.model.Teacher;

public enum ClassRoomDtoToClassRoomConverter implements Converter<ClassRoomDto, ClassRoom> {
    INSTANCE;

    @Override
    public ClassRoom convert(ClassRoomDto classRoomDto) {
        TeacherDto teacherDto = classRoomDto.getTeacherDto();
        return ClassRoom.builder()
                .classNumber(classRoomDto.getClassNumber())
                .schoolNumber(classRoomDto.getSchoolNumber())
                .teacher(Teacher.builder()
                        .id(teacherDto.getId())
                        .name(teacherDto.getName())
                        .email(teacherDto.getEmail())
                        .build())
                .build();

    }
}
