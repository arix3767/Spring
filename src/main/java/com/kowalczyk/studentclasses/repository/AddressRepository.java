package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
