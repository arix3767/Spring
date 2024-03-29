package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByFlatNumber(int flatNumber);
    Address findByFlatNumber(int flatNumber);

}
