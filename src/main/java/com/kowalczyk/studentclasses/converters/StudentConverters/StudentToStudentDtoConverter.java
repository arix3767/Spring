package com.kowalczyk.studentclasses.converters.StudentConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.entity.Address;
import com.kowalczyk.studentclasses.entity.Student;

import java.util.Optional;

public enum StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    INSTANCE;

    @Override
    public StudentDto convert(Student student) {
        Address address = student.getAddress();
        AddressDto addressDto = Optional.ofNullable(address)
                .map(this::convertAddress).orElse(null);
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .name(student.getName())
                .teacher(student.getTeacherName())
                .rate(student.getRate())
                .address(addressDto)
                .build();
    }

    private AddressDto convertAddress(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .homeNumber(address.getHomeNumber())
                .flatNumber(address.getFlatNumber())
                .build();
    }
}
