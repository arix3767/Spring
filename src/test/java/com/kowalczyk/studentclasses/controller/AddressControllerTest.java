package com.kowalczyk.studentclasses.controller;

import com.google.gson.Gson;
import com.kowalczyk.studentclasses.dto.AddressDto;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
@WithMockUser(roles = "STUDENT")
public class AddressControllerTest {

    private static final int flatNumber = 5;
    private static final String ADDRESS_PATH = "/address";
    private static final String ROOT_JSON_PATH = "$";
    private static final String SPECIFIC_ADDRESS_PATH = ADDRESS_PATH + "/" + flatNumber;

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
                .ifPresent(flatNumbers -> flatNumbers.forEach(addressController::deleteAddress));

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

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllAddressesWithRoleStudent() throws Exception {
        testGetAllAddressesWithAnyRole();
    }

    private void testGetAllAddressesWithAnyRole() throws Exception {
        mockMvc.perform(get(ADDRESS_PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"TEACHER"})
    void getAllAddressesWithRoleTeacher() throws Exception {
        testGetAllAddressesWithAnyRole();
    }

    @Test
    void addAddress() throws Exception {
        String json = gson.toJson(buildAddressDto());
        mockMvc.perform(post(ADDRESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.ADDRESS_ADD_SUCCESS.getText()));
    }

    @Test
    void shouldNotAddAddressWhenAddressAlreadyExists() throws Exception {
        AddressDto addressDto = buildAddressDto();
        addressController.addAddress(addressDto);
        String json = gson.toJson(addressDto);
        mockMvc.perform(post(ADDRESS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.ADDRESS_ADD_FAILED.getText()));

    }
    @Test
    void deleteAddress() throws Exception {
        addressController.addAddress(buildAddressDto());
        mockMvc.perform(delete(SPECIFIC_ADDRESS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.ADDRESS_DELETE_SUCCESS.getText()));

    }

    @Test
    void shouldNotDeleteAddressWhenAddressNotExists() throws Exception {
        mockMvc.perform(delete(SPECIFIC_ADDRESS_PATH))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ROOT_JSON_PATH).isNotEmpty())
                .andExpect(jsonPath(ROOT_JSON_PATH).isString())
                .andExpect(jsonPath(ROOT_JSON_PATH).value(Messages.ADDRESS_NOT_FOUND.getText()));

    }

    private AddressDto buildAddressDto() {
        return AddressDto.builder()
                .city("Warszawa")
                .postalCode(123)
                .street("Paryska")
                .homeNumber(12)
                .flatNumber(5)
                .build();
    }
}
