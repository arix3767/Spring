package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
}
