package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    List<Student> findAllByRateLessThan(float rate);
    List<Student> findAllByRateGreaterThan(float rate);
    List<Student> findAllByRateBetween(float lowLevel, float highLevel);
    List<Student> findAllByRateGreaterThanEqual(float rate);
    List<Student> findAllByRateLessThanEqual(float rate);
    List<Student> findAll();

    @Query(value = "SELECT id FROM STUDENT WHERE student.email = :email", nativeQuery = true)
    Long findStudentIdByEmail(@Param("email") String email);

}