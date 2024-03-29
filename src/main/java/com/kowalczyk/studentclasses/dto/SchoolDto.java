package com.kowalczyk.studentclasses.dto;

import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {

    private Long id;
    private int schoolNumber;
    private String websiteUrl;
    private AddressDto addressDto;
    private List<ClassRoomDto> classRoomDtoList;
}