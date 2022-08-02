package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentDtoToStudentConverter;
import com.kowalczyk.studentclasses.converters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest
class StudentServiceTest {

    private static final String EMAIL = "janek";

    @MockBean
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setup() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void getAll() {
        Mockito.when(studentRepository.findAll()).thenReturn(Map.of(EMAIL, buildStudent()));
        Map<String, StudentDto> students = studentService.getAll();
        Assertions.assertEquals(1, students.size());
    }

    @Test
    void addStudent() {
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
        studentService.addStudent(StudentToStudentDtoConverter.INSTANCE.convert(buildStudent()));



    }

    private Student buildStudent() {
        return Student.builder()
                .name("Janek")
                .email(EMAIL)
                .teacher("Mati")
                .rate(5.0f)
                .build();
    }
}
