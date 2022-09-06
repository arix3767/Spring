package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String email);
}
