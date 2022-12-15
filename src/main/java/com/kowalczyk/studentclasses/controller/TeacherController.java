package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.annotation.TeacherDataExtendedAuthorization;
import com.kowalczyk.studentclasses.dto.TeacherDto;
import com.kowalczyk.studentclasses.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        return ResponseEntity.ok(teacherService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> addTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.addTeacher(teacherDto);
        return ResponseEntity.ok("Teacher added");
    }

    // TODO zaimplementuj funkcjonalność
    @TeacherDataExtendedAuthorization
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findTeacher(@PathVariable long id) {
        return null;
    }

    // TODO zaimplementuj funkcjonalność
    @TeacherDataExtendedAuthorization
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeacher(@PathVariable long id, @RequestBody TeacherDto teacherDto) {
        return null;
    }
}
