package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
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

    private final Map<String, Student> students = new HashMap<>();

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Map<String, Student>> getAll() {
        Map<String, Student> studentMap = studentService.getAll();
        return ResponseEntity.ok(studentMap);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        String message = studentService.addStudent(student);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Student> findStudent(@PathVariable String email) {
        Student student = studentService.findStudent(email);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> editStudent(@PathVariable String email, @RequestBody Student newStudentData) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        if (newStudentData.getEmail() == null) {
            return ResponseEntity.badRequest().body(Messages.INVALID_EMAIL.getText());
        }
        Student student = students.get(email);
        student.setName(newStudentData.getName());
        student.setRate(newStudentData.getRate());
        student.setTeacher(newStudentData.getTeacher());
        student.setEmail(newStudentData.getEmail());
        students.remove(email);
        students.put(newStudentData.getEmail(),student);
        return ResponseEntity.ok(Messages.STUDENT_EDIT_SUCCESS.getText());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(@PathVariable String email) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        Student student = students.get(email);
        students.remove(email, student);
        return ResponseEntity.ok(Messages.STUDENT_DELETE_SUCCESS.getText());
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> updateRate(@PathVariable String email, @RequestBody float rate) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        Student student = students.get(email);
        student.setRate(rate);
        return ResponseEntity.ok(Messages.STUDENT_UPDATE_RATE_SUCCESS.getText());
    }
}
