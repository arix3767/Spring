package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentDtoToStudentConverter;
import com.kowalczyk.studentclasses.converters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Verify if get all works correctly")
    void getAll() {
        // given
        Mockito.when(studentRepository.findAll()).thenReturn(Map.of(EMAIL, buildStudent()));
        // when
        Map<String, StudentDto> students = studentService.getAll();
        // then
        // when + then -> w jednej sekcji expect
        Assertions.assertEquals(1, students.size());
    }

    @Test
    void addStudent() {
        // given
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
        Student student = buildStudent();
        // when
        studentService.addStudent(StudentToStudentDtoConverter.INSTANCE.convert(student));
        // then
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
    }

    @Test
    void editStudent() {
        // given
        Student student = buildStudent();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(student);
        //when
        studentService.editStudent(EMAIL, StudentToStudentDtoConverter.INSTANCE.convert(student));
        //then
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
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
