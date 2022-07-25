package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
import com.kowalczyk.studentclasses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Map<String, Student> getAll() {
        return studentRepository.findAll();
    }

    public String addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentAlreadyExistsException();
        }
        studentRepository.save(student);
        return Messages.STUDENT_ADD_SUCCESS.getText();
    }

    public Student findStudent(String email) {
        if (!studentRepository.existsByEmail(email)) {
            throw new StudentNotFoundException();
        }
        return studentRepository.findByEmail(email);
    }

    public String editStudent(String email, Student newStudentData) {
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
                .newEmail(newStudentData.getNewEmail())
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

}
