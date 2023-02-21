package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.annotation.StudentDataExtendedAuthorization;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.service.RealStudentService;
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
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto student = studentService.addStudent(studentDto);
        return ResponseEntity.ok(student);
    }

    @StudentDataExtendedAuthorization
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> findStudent(@PathVariable long id) {
        StudentDto studentDto = studentService.findStudent(id);
        return ResponseEntity.ok(studentDto);
    }

    @StudentDataExtendedAuthorization
    @PutMapping("/{id}")
    public ResponseEntity<String> editStudent(@PathVariable long id, @RequestBody StudentDto newStudentData) {
        String message = studentService.editStudent(id, newStudentData);
        return ResponseEntity.ok(message);
    }

    @StudentDataExtendedAuthorization
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id) {
        String message = studentService.deleteStudent(id);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateRate(@PathVariable long id, @RequestBody float rate) {
        String message = studentService.updateRate(id, rate);
        return ResponseEntity.ok(message);
    }
}
