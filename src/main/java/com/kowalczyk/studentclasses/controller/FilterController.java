package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.service.RealStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/filter")
public class FilterController {

    private final RealStudentService studentService;

    @GetMapping("/lessThan")
    public ResponseEntity<List<StudentDto>> findAllLessThan(@RequestParam float rate) {
        List<StudentDto> students = studentService.findAllLessThan(rate);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/greaterThan")
    public ResponseEntity<List<StudentDto>> findAllGreaterThan(@RequestParam float rate) {
        List<StudentDto> students = studentService.findAllByRateGreaterThan(rate);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/between")
    public ResponseEntity<List<StudentDto>> findAllByRateBetween(@RequestParam float lowLevel, @RequestParam float highLevel) {
        List<StudentDto> students = studentService.findAllByRateBetween(lowLevel, highLevel);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/greaterThanEqual")
    public ResponseEntity<List<StudentDto>> findAllGreaterThanEqual(@RequestParam float rate) {
        List<StudentDto> students = studentService.findAllByRateGreaterThanEqual(rate);
        return ResponseEntity.ok(students);

    }

    @GetMapping("/lessThanEqual")
    public ResponseEntity<List<StudentDto>> findAllLessThanEqual(@RequestParam float rate) {
        List<StudentDto> students = studentService.findAllByRateLessThanEqual(rate);
        return ResponseEntity.ok(students);
    }
}
