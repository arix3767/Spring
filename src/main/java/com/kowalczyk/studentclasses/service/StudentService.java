package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.InvalidEmailException;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.exception.StudentNotFoundException;
import com.kowalczyk.studentclasses.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final Map<String, Student> students = new HashMap<>();

    public Map<String, Student> getAll() {
        return students;
    }

    public String addStudent(Student student) {
        if (students.containsKey(student.getEmail())) {
            throw new StudentAlreadyExistsException();
        }
        students.put(student.getEmail(), student);
        return Messages.STUDENT_ADD_SUCCESS.getText();
    }

    public Student findStudent(String email) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        return students.get(email);
    }

    public String editStudent(String email, Student newStudentData) {
        if (!students.containsKey(email)) {
            throw new StudentNotFoundException();
        }
        if (newStudentData.getEmail() == null) {
            throw new InvalidEmailException();
        }
        Student student = students.get(email).toBuilder()
                .name(newStudentData.getName())
                .rate(newStudentData.getRate())
                .teacher(newStudentData.getTeacher())
                .email(newStudentData.getEmail())
                .build();
        students.remove(email);
        students.put(newStudentData.getEmail(),student);
        return Messages.STUDENT_EDIT_SUCCESS.getText();
    }

}
