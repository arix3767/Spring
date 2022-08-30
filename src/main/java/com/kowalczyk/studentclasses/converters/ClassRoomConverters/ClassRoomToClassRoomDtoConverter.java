package com.kowalczyk.studentclasses.converters.ClassRoomConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.model.ClassRoom;
import com.kowalczyk.studentclasses.model.Teacher;

public enum ClassRoomToClassRoomDtoConverter implements Converter<ClassRoom, ClassRoomDto> {

    INSTANCE;

    @Override
    public ClassRoomDto convert(ClassRoom classRoom) {
        Teacher teacher = classRoom.getTeacher();
        return ClassRoomDto.builder()
                .classNumber(classRoom.getClassNumber())
                .schoolNumber(classRoom.getSchoolNumber())
                .teacherDto(TeacherDto.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .email(teacher.getEmail())
                        .build())
                .build();

    }
}
