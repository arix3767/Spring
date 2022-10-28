package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.AddressConverters.AddressDtoToAddressConverter;
import com.kowalczyk.studentclasses.converters.AddressConverters.AddressToAddressDtoConverter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.entity.Address;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.AddressAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.AddressNotFoundException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressDto> getAll() {
        return addressRepository.findAll().stream()
                .map(AddressToAddressDtoConverter.INSTANCE::convert)
                .toList();
    }

    public String addAddress(AddressDto addressDto) {
        if (addressRepository.existsByFlatNumber(addressDto.getFlatNumber())) {
            throw new AddressAlreadyExistsException();
        }
        Address address = AddressDtoToAddressConverter.INSTANCE.convert(addressDto);
        addressRepository.save(address);
        return Messages.ADDRESS_ADD_SUCCESS.getText();
    }

    public String deleteAddress(int flatNubmer) {
        if (!addressRepository.existsByFlatNumber(flatNubmer)) {
            throw new AddressNotFoundException();
        } else {
            Address address = addressRepository.findByFlatNumber(flatNubmer);
            addressRepository.delete(address);
            return Messages.ADDRESS_DELETE_SUCCESS.getText();
        }
    }
}
