package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);
    boolean existsByEmail(String email);
    List<Student> findAllByRateLessThan(float rate);
    List<Student> findAllByRateGreaterThan(float rate);
    List<Student> findAllByRateBetween(float lowLevel, float highLevel);
}