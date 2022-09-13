package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassesRepository extends JpaRepository<Classes,Long> {
}
