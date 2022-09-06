package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.UserDataDto;
import com.kowalczyk.studentclasses.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userdata")
public class UserDataController {

    private final UserDataService userDataService;

    @GetMapping
    public ResponseEntity<List<UserDataDto>> getAll() {
        List<UserDataDto> userData = userDataService.getAll();
        return ResponseEntity.ok(userData);
    }

    public ResponseEntity<String> addUserData(@RequestBody UserDataDto userDataDto) {
        userDataService.addUserData(userDataDto);
        return ResponseEntity.ok("User data added");
        
    }
}
