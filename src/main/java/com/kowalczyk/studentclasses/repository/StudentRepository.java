package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.model.Student;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StudentRepository {

    Map<String, Student> findAll();
    Student save(Student student);
    Student findByEmail(String email);
    void delete(Student student);
    boolean existsByEmail(String email);
}
