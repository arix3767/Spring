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

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Map<String, StudentDto> getAll() {
        return studentRepository.findAll().stream()
                .collect(Collectors.toMap(Student::getEmail, StudentToStudentDtoConverter.INSTANCE::convert));
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
      StudentDto studentDto = StudentToStudentDtoConverter.INSTANCE.convert(studentRepository.findByEmail(email).toBuilder()
              .name(newStudentData.getName())
              .rate(newStudentData.getRate())
              .teacher(newStudentData.getTeacher())
              .newEmail(newStudentData.getEmail())
              .build());

        studentRepository.save(StudentDtoToStudentConverter.INSTANCE.convert(studentDto));
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
        StudentDto studentDto = StudentToStudentDtoConverter.INSTANCE.convert(studentRepository.findByEmail(email));
        studentDto.setRate(rate);
        studentRepository.save(StudentDtoToStudentConverter.INSTANCE.convert(studentDto));
        return Messages.STUDENT_UPDATE_RATE_SUCCESS.getText();
    }

}
