package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentDtoToStudentConverter;
import com.kowalczyk.studentclasses.converters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Student> expectedStudents = Collections.singletonList(buildStudent());
        Mockito.when(studentRepository.findAll()).thenReturn(expectedStudents);
        // when
        Map<String, StudentDto> students = studentService.getAll();
        // then
        assertEquals(expectedStudents.size(), students.size());
        students.forEach((email, studentDto) -> {
            Student expectedStudentDto = expectedStudents.stream()
                    .filter(student -> email.equals(student.getEmail()))
                    .findFirst()
                    .orElse(null);
            assertNotNull(expectedStudentDto);
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
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
        assertEquals(Messages.STUDENT_ADD_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotAddAlreadyExistingStudent() {
        //given
        Student student = buildStudent();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        //when
        assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(StudentToStudentDtoConverter.INSTANCE.convert(student)));
        //then
        Mockito.verify(studentRepository, Mockito.times(0)).save(student);

    }

    @Test
    void editStudent() {
        // given
        Student student = buildStudent();
        Student editedStudent = buildStudent().toBuilder().rate(2.0f).build();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(student);
        //when
        String message = studentService.editStudent(EMAIL, StudentToStudentDtoConverter.INSTANCE.convert(editedStudent));
        //then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
        assertEquals(Messages.STUDENT_EDIT_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotEditStudentWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
//        when
        assertThrows(StudentNotFoundException.class, () -> studentService.editStudent(null, buildStudentDto()));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any(Student.class));
    }

    @Test
    void shouldNotEditStudentWhenEmailIsNull() {
//        given
        Student editedStudent = buildStudent().toBuilder().email(null).build();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
//        when
        assertThrows(InvalidEmailException.class, () -> studentService.editStudent(EMAIL, StudentToStudentDtoConverter.INSTANCE.convert(editedStudent)));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any(Student.class));
    }

    @Test
    void findStudent() {
        //given
        StudentDto expectedStudent = buildStudentDto();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(StudentDtoToStudentConverter.INSTANCE.convert(expectedStudent));
        //when
        StudentDto student = studentService.findStudent(EMAIL);
        //then
        Mockito.verify(studentRepository).findByEmail(EMAIL);
        assertEquals(expectedStudent, student);
    }

    @Test
    void shouldNotFindStudentWhenStudentDoesNotExist() {
//        give
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
//        when
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudent(EMAIL));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).findByEmail(EMAIL);
    }

    @Test
    void deleteStudent() {
        //given
        Student student = buildStudent();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(student);
        //when
        String message = studentService.deleteStudent(EMAIL);
        //then
        Mockito.verify(studentRepository).delete(student);
        assertEquals(Messages.STUDENT_DELETE_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotDeleteStudentWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
//        when
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(EMAIL));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).delete(Mockito.any(Student.class));
    }

    @Test
    void updateRate() {
//        given
        Student student = buildStudent();
        float oldRate = student.getRate();
        float newRate = 4;
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(student);
//        when
        String message = studentService.updateRate(EMAIL, newRate);
//        then
        assertNotEquals(oldRate, newRate);
        assertEquals(Messages.STUDENT_UPDATE_RATE_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotUpdateRateWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
//        when
        assertThrows(StudentNotFoundException.class, () -> studentService.updateRate(EMAIL, 4));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any(Student.class));
    }

    private Student buildStudent() {
        return Student.builder()
                .name("Janek")
                .email(EMAIL)
                .teacher("Mati")
                .rate(5.0f)
                .build();
    }

    private StudentDto buildStudentDto() {
        return StudentDto.builder()
                .name("janek")
                .email(EMAIL)
                .teacher("Mati")
                .rate(5.0f)
                .build();
    }
}
