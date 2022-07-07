package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        students.add(Student.builder()
                .name("Janek")
                .teacher("Mateusz")
                .email("a@a.com")
                .rate(5.0f).build());
        return ResponseEntity.ok(students);
    }
}
