package com.kowalczyk.studentclasses.service;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Mockito.when(studentRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        // when
        studentService.addStudent(buildStudentDto());
        // then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }

    private void mockSecurity() {
        SecurityContext securityContext = mock(SecurityContext.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        String user = "admin";
        when(authentication.getName()).thenReturn(user);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authentication.isAuthenticated()).thenReturn(true);
        ServletRequestAttributes servletRequestAttributes = mock(ServletRequestAttributes.class);
        requestContextHolderMockedStatic.when(RequestContextHolder::getRequestAttributes)
                .thenReturn(servletRequestAttributes);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);
        String header = "Authorization";
        HttpServletRequest request = servletRequestAttributes.getRequest();
        when(request.getHeader(header)).thenReturn("1234");


    }

    @Test
    void addStudent() {
        // given
        mockSecurity();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
        // when
        String message = studentService.addStudent(buildStudentDto());
        // then
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
        assertEquals(Messages.STUDENT_ADD_SUCCESS.getText(), message);
    }

    @Test
    void shouldNotAddAlreadyExistingStudent() {
        //given
        mockSecurity();
        Student student = buildStudent();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        //when
        assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(buildStudentDto()));
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
        assertThrows(UserNotFoundException.class, () -> studentService.editStudent(null, buildStudentDto()));
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
        mockSecurity();
        StudentDto expectedStudent = buildStudentDto();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(true);
        Mockito.when(studentRepository.findByEmail(EMAIL)).thenReturn(buildStudent());
        //when
        StudentDto student = studentService.findStudent(EMAIL);
        //then
        Mockito.verify(studentRepository).findByEmail(EMAIL);
        assertEquals(expectedStudent, student);
    }

    @Test
    void shouldNotFindStudentWhenStudentDoesNotExist() {
//        give
        mockSecurity();
        Mockito.when(studentRepository.existsByEmail(EMAIL)).thenReturn(false);
//        when
        assertThrows(UserNotFoundException.class, () -> studentService.findStudent(EMAIL));
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
        assertThrows(UserNotFoundException.class, () -> studentService.deleteStudent(EMAIL));
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
        assertThrows(UserNotFoundException.class, () -> studentService.updateRate(EMAIL, 4));
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
