package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAll();

    StudentDto addStudent(StudentDto studentDto);

    StudentDto findStudent(long id);

    String editStudent(long id, StudentDto newStudentData);

    String deleteStudent(long id);

    String updateRate(long id, float rate);

    List<StudentDto> findAllLessThan(float rate);

    List<StudentDto> findAllByRateGreaterThan(float rate);

    List<StudentDto> findAllByRateBetween(float lowLevel, float highLevel);

    List<StudentDto> findAllByRateGreaterThanEqual(float rate);

    List<StudentDto> findAllByRateLessThanEqual(float rate);

}
