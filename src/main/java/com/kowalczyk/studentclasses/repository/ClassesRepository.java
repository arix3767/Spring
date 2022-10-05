package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,Long> {
}
