package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Map<String, StudentDto>> getAll() {
        Map<String, StudentDto> studentMap = studentService.getAll();
        return ResponseEntity.ok(studentMap);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody StudentDto student) {
        String message = studentService.addStudent(student);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{email}")
    public ResponseEntity<StudentDto> findStudent(@PathVariable String email) {
        StudentDto studentDto = studentService.findStudent(email);
        return ResponseEntity.ok(studentDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> editStudent(@PathVariable String email, @RequestBody StudentDto newStudentData) {
      String message = studentService.editStudent(email,newStudentData);
      return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(@PathVariable String email) {
        String message = studentService.deleteStudent(email);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> updateRate(@PathVariable String email, @RequestBody float rate) {
        String message = studentService.updateRate(email,rate);
        return ResponseEntity.ok(message);
    }
}
