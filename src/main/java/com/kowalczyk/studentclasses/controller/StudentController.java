package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
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

    @PutMapping("/{email}")
    public ResponseEntity<String> editStudent(@PathVariable String email, @RequestBody Student newStudentData) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        if (newStudentData.getEmail() == null) {
            return ResponseEntity.badRequest().body("email cannot be null");
        }
        Student student = students.get(email);
        student.setName(newStudentData.getName());
        student.setRate(newStudentData.getRate());
        student.setTeacher(newStudentData.getTeacher());
        student.setEmail(newStudentData.getEmail());
        students.remove(email);
        students.put(newStudentData.getEmail(),student);
        return ResponseEntity.ok("student data changed successfuly");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(@PathVariable String email) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        Student student = students.get(email);
        students.remove(email, student);
        return ResponseEntity.ok("Student removed successfuly");
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> updateRate(@PathVariable String email, @RequestBody float rate) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        Student student = students.get(email);
        student.setRate(rate);
        return ResponseEntity.ok("Rate changed successfuly");
    }
}
