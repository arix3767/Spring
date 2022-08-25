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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDto> getAll() {
        return studentRepository.findAll().stream()
                .map(StudentToStudentDtoConverter.INSTANCE::convert)
                .toList();
    }

    public String addStudent(StudentDto student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentAlreadyExistsException();
        }
        studentRepository.save(StudentDtoToStudentConverter.INSTANCE.convert(student));
        return Messages.STUDENT_ADD_SUCCESS.getText();
    }

    public StudentDto findStudent(String email) {
        if (!studentRepository.existsByEmail(email)) {
            throw new StudentNotFoundException();
        }
        return StudentToStudentDtoConverter.INSTANCE.convert(studentRepository.findByEmail(email));
    }

    public String editStudent(String email, StudentDto newStudentData) {
        if (!studentRepository.existsByEmail(email)) {
            throw new StudentNotFoundException();
        }
        if (newStudentData.getEmail() == null) {
            throw new InvalidEmailException();
        }
        Student student = studentRepository.findByEmail(email).toBuilder()
                .name(newStudentData.getName())
                .rate(newStudentData.getRate())
                .teacher(newStudentData.getTeacher())
                .build();

        studentRepository.save(student);
        return Messages.STUDENT_EDIT_SUCCESS.getText();
    }

    public String deleteStudent(String email) {
        if (!studentRepository.existsByEmail(email)) {
            throw new StudentNotFoundException();
        }
        Student student = studentRepository.findByEmail(email);
        studentRepository.delete(student);
        return Messages.STUDENT_DELETE_SUCCESS.getText();
    }

    public String updateRate(String email, float rate) {
        if (!studentRepository.existsByEmail(email)) {
            throw new StudentNotFoundException();
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
