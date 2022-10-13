package com.kowalczyk.studentclasses.controller;

import com.google.gson.Gson;
import com.kowalczyk.studentclasses.dto.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    private static final String ADDRESS_PATH = "/address";
    private static final String ROOT_JSON_PATH = "$";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AddressController addressController;

    @Autowired
    private Gson gson;

    @BeforeEach
    void setup() {
        Optional.ofNullable(addressController.getAll())
                .map(HttpEntity::getBody)
                .filter(addresses -> addresses.size() > 0)
                .map(addressDtoList -> addressDtoList.stream()
                        .map(AddressDto::getFlatNumber)
                        .toList())
                .ifPresent(flatNubmers -> flatNubmers.forEach(addressController::deleteAddress));

    }

    @Test
    @WithMockUser(roles = {"ADMIN", "TEACHER"})
    void getAllAddressesWhenNoAddressExist() throws Exception {
        mockMvc.perform(get(ADDRESS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(new ArrayList<>()));
    }


    AddressDto addressDto = AddressDto.builder()
            .city("Warszawa")
            .postalCode(123)
            .street("Paryska")
            .homeNumber(12)
            .flatNumber(5)
            .build();
}
