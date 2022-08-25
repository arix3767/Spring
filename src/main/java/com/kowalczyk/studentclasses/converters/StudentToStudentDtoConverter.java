package com.kowalczyk.studentclasses.converters;

import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.model.Address;
import com.kowalczyk.studentclasses.model.Student;

public enum StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    INSTANCE;

    @Override
    public StudentDto convert(Student student) {
        Address address = student.getAddress();
        return StudentDto.builder()
                .email(student.getEmail())
                .name(student.getName())
                .teacher(student.getTeacherName())
                .rate(student.getRate())
                .address(AddressDto.builder()
                        .city(address.getCity())
                        .postalCode(address.getPostalCode())
                        .street(address.getStreet())
                        .homeNumber(address.getHomeNumber())
                        .flatNumber(address.getFlatNumber())
                        .build())
                .build();
    }
}
