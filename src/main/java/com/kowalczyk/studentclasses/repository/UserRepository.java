package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, Long> {
}
