package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Map<String, Student> expectedStudents = Collections.singletonMap(EMAIL, buildStudent());
        Mockito.when(studentRepository.findAll()).thenReturn(expectedStudents);
        // when
        Map<String, StudentDto> students = studentService.getAll();
        // then
        assertEquals(expectedStudents.size(), students.size());
        students.forEach((email, studentDto) -> {
            Student expectedStudentDto = expectedStudents.get(email);
            assertEquals(expectedStudentDto.getEmail(), studentDto.getEmail());
            assertEquals(expectedStudentDto.getName(), studentDto.getName());
            assertEquals(expectedStudentDto.getTeacher(), studentDto.getTeacher());
            assertEquals(expectedStudentDto.getRate(), studentDto.getRate());
        });
    }

    @Test
    void addAnyStudent() {
        // given
        Mockito.when(studentRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        // when
        studentService.addStudent(Mockito.mock(StudentDto.class));
        // then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }

    @Test
    void addStudent() {
        // given
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
        Student student = buildStudent();
        // when
        String message = studentService.addStudent(StudentToStudentDtoConverter.INSTANCE.convert(student));
        // then
        Mockito.verify(studentRepository).save(student);
        assertEquals(Messages.STUDENT_ADD_SUCCESS.getText(), message);
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

    @Test
    void findStudent() {

    }

    @Test
    void deleteStudent() {

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
