package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAll() {
        List<AddressDto> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<String> addAddress(@RequestBody AddressDto addressDto) {
        String message = addressService.addAddress(addressDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{flatNumber}")
    public ResponseEntity<String> deleteAddress(@PathVariable int flatNumber) {
        String message = addressService.deleteAddress(flatNumber);
        return ResponseEntity.ok(message);
    }
}
