package com.kowalczyk.studentclasses.converters.StudentConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.model.Address;
import com.kowalczyk.studentclasses.model.Student;

public enum StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

    INSTANCE;

    @Override
    public Student convert(StudentDto studentDto) {
        AddressDto addressDto = studentDto.getAddress();
        return Student.builder()
                .email(studentDto.getEmail())
                .name(studentDto.getName())
                .teacherName(studentDto.getTeacher())
                .rate(studentDto.getRate())
                .address(Address.builder()
                        .city(addressDto.getCity())
                        .postalCode(addressDto.getPostalCode())
                        .street(addressDto.getStreet())
                        .homeNumber(addressDto.getHomeNumber())
                        .flatNumber(addressDto.getFlatNumber())
                        .build())
                .build();
    }
}
