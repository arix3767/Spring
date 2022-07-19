package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.enums.Messages;
import com.kowalczyk.studentclasses.exception.StudentAlreadyExistsException;
import com.kowalczyk.studentclasses.model.Student;
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
}
