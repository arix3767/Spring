package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.converters.AddressConverters.AddressDtoToAddressConverter;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.entity.Address;
import com.kowalczyk.studentclasses.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/adress")
public class AddressController {

    private final AddressService addressService;

    public ResponseEntity<List<AddressDto>> getAll() {
        List<AddressDto> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }

    public ResponseEntity<String> addAddress(@RequestBody AddressDto addressDto) {
        addressService.addAddress(addressDto);
        return ResponseEntity.ok("Address added");
    }
}
