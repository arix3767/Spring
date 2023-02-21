package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.configuration.Profiles;
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
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Conditional(RealStudentService.MyCondition.class)
@Log4j2
@RequiredArgsConstructor
@Service
public class RealStudentService implements StudentService {

    public static class MyCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String[] activeProfiles = context.getEnvironment().getActiveProfiles();
            Set<String> profilesSet = Arrays.stream(activeProfiles)
                    .collect(Collectors.toSet());
            return !profilesSet.contains(Profiles.EXPERIMENTAL);
        }
    }

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void setUp() {
        log.info("{} has been initialized", this.getClass().getSimpleName());
    }

    @Override
    public List<StudentDto> getAll() {
        log.info("Fetching students...");
        return studentRepository.findAll().stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
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
        return StudentToStudentDtoConverter.INSTANCE.convert(student);
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

    @Override
    public StudentDto findStudent(long id) {
        log.info("Looking for student with id {}", id);
        logSecurityInfo();
        Student student = studentRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return StudentToStudentDtoConverter.INSTANCE.convert(student);
    }

    @Override
    public String editStudent(long id, StudentDto newStudentData) {
        log.info("Editing student with id {}...", id);
        if (newStudentData.getEmail() == null) {
            log.error("Cannot edit student. Email must not be null.");
            throw new InvalidEmailException();
        }
        Student student = studentRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .toBuilder()
                .email(newStudentData.getEmail())
                .password(newStudentData.getPassword())
                .name(newStudentData.getName())
                .teacherName(newStudentData.getTeacher())
                .build();
        encodePassword(student);
        studentRepository.save(student);

        return Messages.STUDENT_EDIT_SUCCESS.getText();
    }

    @Override
    public String deleteStudent(long id) {
        log.info("Deleting student with id {}...", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        studentRepository.delete(student);
        return Messages.STUDENT_DELETE_SUCCESS.getText();
    }

    @Override
    public String updateRate(long id, float rate) {
        log.info("Updating rate to {} for student with id {}...", rate, id);
        Student student = studentRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        student.setRate(rate);
        studentRepository.save(student);
        return Messages.STUDENT_UPDATE_RATE_SUCCESS.getText();
    }

    @Override
    public List<StudentDto> findAllLessThan(float rate) {
        return studentRepository.findAllByRateLessThan(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    @Override
    public List<StudentDto> findAllByRateGreaterThan(float rate) {
        return studentRepository.findAllByRateGreaterThan(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    @Override
    public List<StudentDto> findAllByRateBetween(float lowLevel, float highLevel) {
        return studentRepository.findAllByRateBetween(lowLevel, highLevel).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    @Override
    public List<StudentDto> findAllByRateGreaterThanEqual(float rate) {
        return studentRepository.findAllByRateGreaterThanEqual(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    @Override
    public List<StudentDto> findAllByRateLessThanEqual(float rate) {
        return studentRepository.findAllByRateLessThanEqual(rate).stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }
}
