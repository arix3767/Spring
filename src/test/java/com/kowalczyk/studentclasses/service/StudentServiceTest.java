package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.Utils.SecurityUtils;
import com.kowalczyk.studentclasses.converters.StudentConverters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mockStatic;

@SpringJUnitConfig
class StudentServiceTest {

    private static final String EMAIL = "janek";
    private static final String NAME = "Janek";
    private static final String PASSWORD = "1234";
    private static final String TEACHER = "Mati";
    private static final float RATE = 5.0f;

    private final MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic =
            mockStatic(SecurityContextHolder.class);

    private final MockedStatic<RequestContextHolder> requestContextHolderMockedStatic =
            mockStatic(RequestContextHolder.class);

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    private StudentService studentService;

    @BeforeEach
    void setup() {
        studentService = new StudentService(studentRepository, passwordEncoder);
    }

    @AfterEach
    void cleanup() {
        securityContextHolderMockedStatic.close();
        requestContextHolderMockedStatic.close();
    }

    @Test
    @DisplayName("Verify if get all works correctly")
    void getAll() {
        // given
        List<Student> expectedStudents = Collections.singletonList(buildStudent());
        Mockito.when(studentRepository.findAll()).thenReturn(expectedStudents);
        // when
        List<StudentDto> students = studentService.getAll();
        // then
        assertEquals(expectedStudents.size(), students.size());
        students.forEach(studentDto -> {
            String email = studentDto.getEmail();
            Student expectedStudentDto = expectedStudents.stream()
                    .filter(student -> email.equals(student.getEmail()))
                    .findFirst()
                    .orElse(null);
            assertNotNull(expectedStudentDto);
            assertEquals(expectedStudentDto.getEmail(), studentDto.getEmail());
            assertEquals(expectedStudentDto.getName(), studentDto.getName());
            assertEquals(expectedStudentDto.getTeacherName(), studentDto.getTeacher());
            assertEquals(expectedStudentDto.getRate(), studentDto.getRate());
        });
    }

    @Test
    void addAnyStudent() {
        // given
        mockSecurity();
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
        // when
        studentService.addStudent(buildStudentDto());
        // then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }

    private void mockSecurity() {
        SecurityUtils.mockSecurityContextHolder(securityContextHolderMockedStatic, EMAIL);
        SecurityUtils.mockServletContextHolder(requestContextHolderMockedStatic);
    }

    @Test
    void addStudent() {
        // given
        mockSecurity();
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
        // when
        StudentDto studentDto = studentService.addStudent(buildStudentDto());
        // then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }

    @Test
    void shouldNotAddAlreadyExistingStudent() {
        //given
        mockSecurity();
        Student student = buildStudent();
        Mockito.when(studentRepository.existsByEmail(student.getEmail())).thenReturn(true);
        //when
        assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(buildStudentDto()));
        //then
        Mockito.verify(studentRepository, Mockito.times(0)).save(student);

    }

    @Test
    void editStudent() {
        // given
        Student student = buildStudent().toBuilder()
                .id(1L)
                .build();
        Student editedStudent = buildStudent().toBuilder()
                .id(1L)
                .rate(2.0f)
                .build();
        Mockito.when(studentRepository.existsById(student.getId())).thenReturn(true);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        //when
        String message = studentService.editStudent(student.getId(), StudentToStudentDtoConverter.INSTANCE.convert(editedStudent));
        //then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
        assertEquals(Messages.STUDENT_EDIT_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotEditStudentWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
//        when
        assertThrows(UserNotFoundException.class, () -> studentService.editStudent(1, buildStudentDto()));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any(Student.class));
    }

    @Test
    void shouldNotEditStudentWhenEmailIsNull() {
//        given
        Student editedStudent = buildStudent().toBuilder()
                .id(1L)
                .email(null)
                .build();
        StudentDto editedStudentDto = StudentToStudentDtoConverter.INSTANCE.convert(editedStudent);
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(true);
//        when
        assertThrows(InvalidEmailException.class, () -> studentService.editStudent(editedStudent.getId(), editedStudentDto));
//        then
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    }

    @Test
    void findStudent() {
        //given
        mockSecurity();
        StudentDto expectedStudent = buildStudentDto().toBuilder()
                .id(1L)
                .build();
        Mockito.when(studentRepository.existsById(expectedStudent.getId())).thenReturn(true);
        Mockito.when(studentRepository.findById(expectedStudent.getId())).thenReturn(Optional.of(buildStudent()
                .toBuilder()
                .id(1L)
                .build()));
        //when
        StudentDto student = studentService.findStudent(expectedStudent.getId());
        //then
        Mockito.verify(studentRepository).findById(expectedStudent.getId());
        assertEquals(expectedStudent, student);
    }

    @Test
    void shouldNotFindStudentWhenStudentDoesNotExist() {
//        give
        mockSecurity();
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
//        when
        assertThrows(UserNotFoundException.class, () -> studentService.findStudent(1));
//        then
        Mockito.verify(studentRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void deleteStudent() {
        //given
        Student student = buildStudent().toBuilder()
                .id(1L)
                .build();
        Mockito.when(studentRepository.existsById(student.getId())).thenReturn(true);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        //when
        String message = studentService.deleteStudent(student.getId());
        //then
        Mockito.verify(studentRepository).delete(student);
        assertEquals(Messages.STUDENT_DELETE_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotDeleteStudentWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
//        when
        assertThrows(UserNotFoundException.class, () -> studentService.deleteStudent(1));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).delete(Mockito.any(Student.class));
    }

    @Test
    void updateRate() {
//        given
        Student student = buildStudent().toBuilder()
                .id(1L)
                .build();
        float oldRate = student.getRate();
        float newRate = 4;
        Mockito.when(studentRepository.existsById(student.getId())).thenReturn(true);
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
//        when
        String message = studentService.updateRate(student.getId(), newRate);
//        then
        assertNotEquals(oldRate, newRate);
        assertEquals(Messages.STUDENT_UPDATE_RATE_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotUpdateRateWhenStudentDoesNotExist() {
//        given
        Mockito.when(studentRepository.existsById(anyLong())).thenReturn(false);
//        when
        assertThrows(UserNotFoundException.class, () -> studentService.updateRate(1, 4));
//        then
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any(Student.class));
    }

    @Test
    void findAllLessThan() {
        //given
        List<Student> expectedStudents = new ArrayList<>();
        float rate = buildStudent().getRate();
        Mockito.when(studentRepository.findAllByRateLessThan(rate)).thenReturn(expectedStudents);
        //when
        List<StudentDto> students = studentService.findAllLessThan(rate);
        //then
        assertEquals(expectedStudents.size(), students.size());
    }

    private Student buildStudent() {
        return Student.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .teacherName(TEACHER)
                .rate(RATE)
                .build();
    }

    private StudentDto buildStudentDto() {
        return StudentDto.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .teacher(TEACHER)
                .rate(RATE)
                .build();
    }
}
