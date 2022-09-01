package com.kowalczyk.studentclasses.converters.ClassRoomConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.dto.SchoolDto;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.model.Address;
import com.kowalczyk.studentclasses.model.ClassRoom;
import com.kowalczyk.studentclasses.model.School;
import com.kowalczyk.studentclasses.model.Teacher;

public enum ClassRoomToClassRoomDtoConverter implements Converter<ClassRoom, ClassRoomDto> {

    INSTANCE;

    @Override
    public ClassRoomDto convert(ClassRoom classRoom) {
        Teacher teacher = classRoom.getTeacher();
        School school = classRoom.getSchool();
        Address address = school.getAddress();
        return ClassRoomDto.builder()
                .classNumber(classRoom.getClassNumber())
                .schoolDto(SchoolDto.builder()
                        .id(school.getId())
                        .schoolNumber(school.getSchoolNumber())
                        .addressDto(AddressDto.builder()
                                .city(address.getCity())
                                .postalCode(address.getPostalCode())
                                .street(address.getStreet())
                                .homeNumber(address.getHomeNumber())
                                .flatNumber(address.getFlatNumber())
                                .build())
                        .build())
                .teacherDto(TeacherDto.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .email(teacher.getEmail())
                        .build())
                .build();

    }
}
