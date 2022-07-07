package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final Map<String, Student> students = new HashMap<>();

    @GetMapping
    public ResponseEntity<Map<String, Student>> getAll() {
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        if (students.containsKey(student.getEmail())) {
            return ResponseEntity.badRequest().body("Student already exists!");
        }
        students.put(student.getEmail(), student);
        return ResponseEntity.ok("Student added successfully!");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Student> findStudent(@PathVariable String email) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        return ResponseEntity.ok(students.get(email));
    }

}
