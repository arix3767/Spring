package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.model.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryStudentRepository implements StudentRepository{

    private final Map<String, Student> students = new HashMap<>();

    @Override
    public Map<String, Student> findAll() {
        return students;
    }

    @Override
    public Student save(Student student) {
        if (students.containsKey(student.getEmail())){
            students.remove(student.getEmail());
            student.setEmail(student.getNewEmail());
            return students.put(student.getNewEmail(),student);
        }
        String email = student.getEmail();
        return students.put(email,student);
    }

    @Override
    public Student findByEmail(String email) {
        return students.get(email);
    }

    @Override
    public void delete(Student student) {
        String email = student.getEmail();
        students.remove(email,student);
    }

    @Override
    public boolean existsByEmail(String email) {
        return students.containsKey(email);
    }
}
