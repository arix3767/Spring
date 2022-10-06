package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        List<StudentDto> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody StudentDto student) {
        String message = studentService.addStudent(student);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{email}")
    public ResponseEntity<StudentDto> findStudent(@PathVariable String email, @RequestHeader(value = "Authorization") String auth) {
        log.info(auth);
        StudentDto studentDto = studentService.findStudent(email);
        return ResponseEntity.ok(studentDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> editStudent(@PathVariable String email, @RequestBody StudentDto newStudentData) {
        String message = studentService.editStudent(email, newStudentData);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(@PathVariable String email) {
        String message = studentService.deleteStudent(email);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> updateRate(@PathVariable String email, @RequestBody float rate) {
        String message = studentService.updateRate(email, rate);
        return ResponseEntity.ok(message);
    }
}
