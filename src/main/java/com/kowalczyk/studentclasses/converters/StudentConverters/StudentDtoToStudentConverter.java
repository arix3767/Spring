package com.kowalczyk.studentclasses.converters.StudentConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.entity.Address;
import com.kowalczyk.studentclasses.entity.Student;

import java.util.Optional;

public enum StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

    INSTANCE;

    @Override
    public Student convert(StudentDto studentDto) {
        AddressDto addressDto = studentDto.getAddress();
        Address address = Optional.ofNullable(addressDto)
                .map(this::convertAddress)
                .orElse(null);
        return Student.builder()
                .email(studentDto.getEmail())
                .password(studentDto.getPassword())
                .name(studentDto.getName())
                .teacherName(studentDto.getTeacher())
                .rate(studentDto.getRate())
                .address(address)
                .build();
    }

    private Address convertAddress(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.getCity())
                .postalCode(addressDto.getPostalCode())
                .street(addressDto.getStreet())
                .homeNumber(addressDto.getHomeNumber())
                .flatNumber(addressDto.getFlatNumber())
                .build();
    }
}
