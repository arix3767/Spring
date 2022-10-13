package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.StudentConverters.StudentDtoToStudentConverter;
import com.kowalczyk.studentclasses.converters.StudentConverters.StudentToStudentDtoConverter;
import com.kowalczyk.studentclasses.dto.StudentDto;
import com.kowalczyk.studentclasses.entity.Student;
import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.MissingDataException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.UserNotFoundException;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public List<StudentDto> getAll() {
        log.info("Fetching students...");
        return studentRepository.findAll().stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public String addStudent(StudentDto studentDto) {
        log.info("Adding student...");
        logSecurityInfo();
        if (studentDto.getEmail() == null || studentDto.getPassword() == null) {
            log.error("Cannot add student. Missing credentials.");
            throw new MissingDataException();
        }
        if (studentRepository.existsByEmail(studentDto.getEmail())) {
            log.error("Cannot add student, email {} exists in database.", studentDto.getEmail());
            throw new StudentAlreadyExistsException();
        }
        Student student = StudentDtoToStudentConverter.INSTANCE.convert(studentDto);
        encodePassword(student);
        studentRepository.save(student);
        return Messages.STUDENT_ADD_SUCCESS.getText();
    }

    private void logSecurityInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        log.info(authentication.getPrincipal().toString());
        log.info(authentication.isAuthenticated());
        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest)
                .map(httpServletRequest -> httpServletRequest.getHeader("Authorization"))
                .ifPresent(log::info);
    }

    private void encodePassword(Student student) {
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
    }

    public StudentDto findStudent(String email) {
        log.info("Looking for student with email {}", email);
        logSecurityInfo();
        if (!studentRepository.existsByEmail(email)) {
            log.error("Cannot find student.");
            throw new UserNotFoundException();
        }
        Student student = studentRepository.findByEmail(email);
        return StudentToStudentDtoConverter.INSTANCE.convert(student);
    }

    public String editStudent(String email, StudentDto newStudentData) {
        log.info("Editing student with email {}...", email);
        if (!studentRepository.existsByEmail(email)) {
            log.error("Cannot edit student. Student not found.");
            throw new UserNotFoundException();
        }
        if (newStudentData.getEmail() == null) {
            log.error("Cannot edit student. Email must not be null.");
            throw new InvalidEmailException();
        }
        Student student = studentRepository.findByEmail(email).toBuilder()
                .email(newStudentData.getEmail())
                .password(newStudentData.getPassword())
                .name(newStudentData.getName())
                .teacherName(newStudentData.getTeacher())
                .build();
        encodePassword(student);
        studentRepository.save(student);

        return Messages.STUDENT_EDIT_SUCCESS.getText();
    }

    public String deleteStudent(String email) {
        log.info("Deleting student with email {}...", email);
        if (!studentRepository.existsByEmail(email)) {
            log.error("Cannot delete student. Student not found");
            throw new UserNotFoundException();
        }
        Student student = studentRepository.findByEmail(email);
        studentRepository.delete(student);
        return Messages.STUDENT_DELETE_SUCCESS.getText();
    }

    public String updateRate(String email, float rate) {
        log.info("Updating rate to {} for student with email {}...", rate, email);
        if (!studentRepository.existsByEmail(email)) {
            log.error("Cannot update student's rate. Student not found.");
            throw new UserNotFoundException();
        }
        Student student = studentRepository.findByEmail(email);
        student.setRate(rate);
        studentRepository.save(student);
        return Messages.STUDENT_UPDATE_RATE_SUCCESS.getText();
    }

    public List<StudentDto> findAllLessThan(float rate) {
        return studentRepository.findAllByRateLessThan(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public List<StudentDto> findAllByRateGreaterThan(float rate) {
        return studentRepository.findAllByRateGreaterThan(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public List<StudentDto> findAllByRateBetween(float lowLevel, float highLevel) {
        return studentRepository.findAllByRateBetween(lowLevel, highLevel).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public List<StudentDto> findAllByRateGreaterThanEqual(float rate) {
        return studentRepository.findAllByRateGreaterThanEqual(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public List<StudentDto> findAllByRateLessThanEqual(float rate) {
        return studentRepository.findAllByRateLessThanEqual(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }
}
