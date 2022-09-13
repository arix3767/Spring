package com.kowalczyk.studentclasses.converters.AddressConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.entity.Address;

public enum AddressToAddressDtoConverter implements Converter<Address, AddressDto> {

    INSTANCE;

    @Override
    public AddressDto convert(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .homeNumber(address.getHomeNumber())
                .flatNumber(address.getFlatNumber())
                .build();
    }
}
