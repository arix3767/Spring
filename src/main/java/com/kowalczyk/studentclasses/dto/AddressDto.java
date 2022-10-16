package com.kowalczyk.studentclasses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String city;
    private int postalCode;
    private String street;
    private int homeNumber;
    private int flatNumber;
}