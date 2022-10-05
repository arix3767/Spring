package com.kowalczyk.studentclasses.converters.AddressConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.entity.Address;

public enum AddressDtoToAddressConverter implements Converter<AddressDto, Address> {

    INSTANCE;

    @Override
    public Address convert(AddressDto addressDto) {
        return Address.builder()
                .id(addressDto.getId())
                .city(addressDto.getCity())
                .postalCode(addressDto.getPostalCode())
                .street(addressDto.getStreet())
                .homeNumber(addressDto.getHomeNumber())
                .flatNumber(addressDto.getFlatNumber())
                .build();
    }
}

