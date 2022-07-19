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
        return null;
    }

    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        return null;
    }

    @Override
    public void delete(Student student) {
    }

    @Override
    public boolean existsByEmail(String email) {
        return students.containsKey(email);
    }
}
