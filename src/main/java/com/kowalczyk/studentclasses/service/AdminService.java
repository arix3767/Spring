package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.entity.Admin;
import com.kowalczyk.studentclasses.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void createSuperAdmin() {
        Admin admin = Admin.builder()
                .email("admin")
                .password(passwordEncoder.encode("admin"))
                .build();
        adminRepository.save(admin);
    }
}
