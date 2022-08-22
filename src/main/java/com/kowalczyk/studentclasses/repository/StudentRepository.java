package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);
    boolean existsByEmail(String email);
}
