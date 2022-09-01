package com.kowalczyk.studentclasses.converters.ClassRoomConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.ClassRoomDto;
import com.kowalczyk.studentclasses.dto.SchoolDto;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.entity.Address;
import com.kowalczyk.studentclasses.entity.ClassRoom;
import com.kowalczyk.studentclasses.entity.School;
import com.kowalczyk.studentclasses.entity.Teacher;

public enum ClassRoomDtoToClassRoomConverter implements Converter<ClassRoomDto, ClassRoom> {
    INSTANCE;

    @Override
    public ClassRoom convert(ClassRoomDto classRoomDto) {
        TeacherDto teacherDto = classRoomDto.getTeacherDto();
        SchoolDto schoolDto = classRoomDto.getSchoolDto();
        AddressDto addressDto = schoolDto.getAddressDto();
        return ClassRoom.builder()
                .classNumber(classRoomDto.getClassNumber())
                .school(School.builder()
                        .id(schoolDto.getId())
                        .schoolNumber(schoolDto.getSchoolNumber())
                        .address(Address.builder()
                                .id(classRoomDto.getId())
                                .city(addressDto.getCity())
                                .postalCode(addressDto.getPostalCode())
                                .street(addressDto.getStreet())
                                .homeNumber(addressDto.getHomeNumber())
                                .flatNumber(addressDto.getFlatNumber())
                                .build())
                        .build())
                .teacher(Teacher.builder()
                        .id(teacherDto.getId())
                        .name(teacherDto.getName())
                        .email(teacherDto.getEmail())
                        .build())
                .build();

    }
}
